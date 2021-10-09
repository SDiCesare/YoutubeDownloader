package com.ike.youtubedownloader.stream;

import com.ike.youtubedownloader.video.YoutubeVideo;
import com.mpatric.mp3agic.BaseException;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Downloader {

    private String dllPath;
    private String outPath;

    public void download(YoutubeVideo video) {
        try {
            System.out.println("Downloading: " + video.getTitle() + "-" + video.getAuthor());
            downloadVideo(getCMD(), video);
            applyTag(video);
        } catch (IOException | InterruptedException | BaseException ex) {
            ex.printStackTrace();
        }
    }

    private void downloadVideo(Process cmd, YoutubeVideo video) throws InterruptedException {
        DownloadCallback downloadCallback = (percent, speed, eta) -> System.out.printf("[download]%s, %s, %s\n", percent, speed, eta);
        new Thread(new SyncPipe(cmd.getInputStream(), System.out, downloadCallback)).start();
        new Thread(new SyncPipe(cmd.getErrorStream(), System.err, downloadCallback)).start();
        PrintWriter printWriter = new PrintWriter(cmd.getOutputStream());
        printWriter.println("cd \"" + this.dllPath + "\"");
        String file = "\"" + this.outPath + "\\song.mp3\"";
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
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        ImageIO.write(video.getThumbnail(), "JPG", imageStream);
        byte[] bytes = imageStream.toByteArray();
        Mp3File song = new Mp3File(songFile);
        ID3v24Tag tag = new ID3v24Tag();
        if (song.hasId3v2Tag() && song.getId3v2Tag() instanceof ID3v24Tag) {
            tag = ((ID3v24Tag) song.getId3v2Tag());
        } else {
            song.setId3v2Tag(tag);
        }
        tag.setAlbumImage(bytes, "Song Cover");
        if (video.getAuthor() != null) {
            tag.setArtist(video.getAuthor());
        }
        if (video.getTitle() != null) {
            tag.setTitle(video.getTitle());
        }
        tag.setComment("Origin: " + video.getURL());
        File out = new File(this.outPath, video.getCode() + ".mp3");
        song.save(out.toString());
        System.out.println("Tag Applied to song");
        System.out.println("Clear cache: " + songFile.delete() + ", " + new File(this.outPath, "song.mp3").delete());
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
