package com.ike.youtubedownloader.stream;

import com.ike.youtubedownloader.stream.callback.DownloadCallback;
import com.ike.youtubedownloader.stream.callback.SimpleDownloaderCallback;
import com.ike.youtubedownloader.util.Settings;
import com.ike.youtubedownloader.video.YoutubeVideo;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Downloader {

    private ArrayList<YoutubeVideo> queue = new ArrayList<>();
    private ArrayList<DownloadCallback> callbacks = new ArrayList<>();

    private String dllPath;
    private String outPath;
    private boolean running;

    public void download(YoutubeVideo video, DownloadCallback callback) {
        this.queue.add(video);
        this.callbacks.add(callback);
        if (!this.running) {
            new Thread(this::downloadProcess).start();
        }
    }

    public void download(YoutubeVideo video) {
        this.download(video, new SimpleDownloaderCallback());
    }

    private void downloadProcess() {
        this.running = true;
        while (!queue.isEmpty()) {
            System.out.println("In Queue: " + queue.size());
            YoutubeVideo video = queue.remove(0);
            DownloadCallback callback = callbacks.remove(0);
            try {
                System.out.println("Downloading: " + video.getTitle() + "-" + video.getAuthor());
                downloadVideo(getCMD(), video, callback);
                applyTag(video);
            } catch (IOException | InterruptedException | BaseException ex) {
                ex.printStackTrace();
            }
            callback.callback(" 100%", "NaN", "NaN");
        }
        this.running = false;
    }

    private void downloadVideo(Process cmd, YoutubeVideo video, DownloadCallback callback) throws InterruptedException {
        new Thread(new SyncPipe(cmd.getInputStream(), System.out, callback)).start();
        new Thread(new SyncPipe(cmd.getErrorStream(), System.err, callback)).start();
        PrintWriter printWriter = new PrintWriter(cmd.getOutputStream());
        printWriter.println("cd \"" + this.dllPath + "\"");
        String file = "\"" + this.outPath + "\\" + video.getCode() + "_origin.mp3\"";
        String downloadCommand = "youtube-dl -x --audio-format mp3 -o " + file + " " + video.getURL();
        printWriter.println(downloadCommand);
        printWriter.println("ffmpeg -i " + file + " -vn -ar 44100 -ac 2 -b:a 192k \"" + this.outPath + "\\" + video.getCode() + "1.mp3\"");
        printWriter.close();
        int i = cmd.waitFor();
        if (i == 0) {
            System.out.println("Song downloaded Successfully");
        } else {
            System.out.println("An error occurred during applying tags");
        }
    }

    private void applyTag(YoutubeVideo video) throws IOException, BaseException {
        File songFile = new File(this.outPath, video.getCode() + "1.mp3");
        if (!songFile.exists()) {
            System.out.println("[error] Can't Apply tag to song " + video.getCode());
            return;
        }
        Mp3File song = new Mp3File(songFile);
        ID3v24Tag tag = new ID3v24Tag();
        if (song.hasId3v2Tag() && song.getId3v2Tag() instanceof ID3v24Tag) {
            tag = ((ID3v24Tag) song.getId3v2Tag());
        } else {
            song.setId3v2Tag(tag);
        }
        BufferedImage thumbnail = video.getThumbnail();
        if (thumbnail != null) {
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "JPG", imageStream);
            byte[] bytes = imageStream.toByteArray();
            tag.setAlbumImage(bytes, "Song Cover");
        }
        String author = video.getAuthor();
        if (author != null) {
            tag.setArtist(author);
        }
        if (video.getTitle() != null) {
            tag.setTitle(video.getTitle());
        }
        tag.setComment("Origin: " + video.getURL());
        String name = video.getCode() + ".mp3";
        if (Settings.get(Settings.SAVE_ARTIST).equals("true") && author != null) {
            File dir = new File(this.outPath, author);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            name = author + "\\" + name;
        }
        File out = new File(this.outPath, name);
        song.save(out.toString());
        System.out.println("Tag Applied to song");
        System.out.println("Clear cache: " + songFile.delete() + ", " + new File(this.outPath, video.getCode() + "_origin.mp3").delete());
    }

    private Process getCMD() throws IOException {
        return Runtime.getRuntime().exec("cmd");
    }

    public String getDllPath() {
        return dllPath;
    }

    public void setDllPath(String dllPath) {
        this.dllPath = dllPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }
}
