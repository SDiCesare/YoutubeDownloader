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
 * The Class that manages the downloads
 *
 * @author Ike
 * @version 1.0A
 **/
public class Downloader {

    /**
     * Each video of the queue has attached a callback
     */
    private final ArrayList<YoutubeVideo> queue = new ArrayList<>();
    private final ArrayList<DownloadCallback> callbacks = new ArrayList<>();
    private boolean running;

    /**
     * Download a video using a callback
     *
     * @param video    The video to download
     * @param callback The callback called for this video
     */
    public void download(YoutubeVideo video, DownloadCallback callback) {
        this.queue.add(video);
        this.callbacks.add(callback);
        if (!this.running) {
            new Thread(this::downloadProcess).start();
        }
    }

    /**
     * USED FOR TEST
     * Download a video using the {@link SimpleDownloaderCallback} as callback
     */
    public void download(YoutubeVideo video) {
        this.download(video, new SimpleDownloaderCallback());
    }

    /**
     * The process called for downloading videos on the queue.
     * It downloads a video, then make a copy of the mp3 for apply mp3 tags and save it on the directory of the downloader.
     */
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
            callback.downloadCallback(" 100%", "NaN", "NaN");
        }
        this.running = false;
    }

    /**
     * Download a Single video
     *
     * @param cmd      The prompt environment used for youtube-dl and ffmpeg commands
     * @param video    The video to download
     * @param callback The callback for the video to download
     */
    private void downloadVideo(Process cmd, YoutubeVideo video, DownloadCallback callback) throws InterruptedException {
        new Thread(new DownloadSyncPipe(cmd.getInputStream(), System.out, callback)).start();
        new Thread(new DownloadSyncPipe(cmd.getErrorStream(), System.err, callback)).start();
        PrintWriter printWriter = new PrintWriter(cmd.getOutputStream());
        String file = "\"" + Settings.get(Settings.DOWNLOAD_DIR) + "\\" + video.getCode() + "_origin.mp3\"";
        String downloadCommand = Settings.get(Settings.YOUTUBE_DL_PATH) + " -x --audio-format mp3 -o " + file + " " + video.getURL();
        printWriter.println(downloadCommand);
        printWriter.println(Settings.get(Settings.FFMPEG_DL_PATH) + " -i " + file + " -vn -ar 44100 -ac 2 -b:a 192k \"" + Settings.get(Settings.DOWNLOAD_DIR) + "\\" + video.getCode() + "1.mp3\"");
        printWriter.close();
        int i = cmd.waitFor();
        if (i == 0) {
            System.out.println("Song downloaded Successfully");
        } else {
            System.out.println("An error occurred during applying tags");
        }
    }

    /**
     * Called after downloading the video.
     * Apply the mp3 tags of the video to his .mp3 file.
     *
     * @param video The video with his tags
     */
    private void applyTag(YoutubeVideo video) throws IOException, BaseException {
        File songFile = new File(Settings.get(Settings.DOWNLOAD_DIR), video.getCode() + "1.mp3");
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
            File dir = new File(Settings.get(Settings.DOWNLOAD_DIR), author);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            name = author + "\\" + name;
        }
        File out = new File(Settings.get(Settings.DOWNLOAD_DIR), name);
        song.save(out.toString());
        System.out.println("Tag Applied to song");
        System.out.println("Clear cache: " + songFile.delete() + ", " + new File(Settings.get(Settings.DOWNLOAD_DIR), video.getCode() + "_origin.mp3").delete());
    }

    /**
     * TODO: 12/11/2021 See if it works on other system
     *
     * @return the prompt environment
     */
    private Process getCMD() throws IOException {
        return Runtime.getRuntime().exec("cmd");
    }
}
