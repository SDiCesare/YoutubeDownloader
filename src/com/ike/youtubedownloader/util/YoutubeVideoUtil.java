package com.ike.youtubedownloader.util;

import com.google.gson.Gson;
import com.ike.youtubedownloader.stream.DownloadSyncPipe;
import com.ike.youtubedownloader.video.YoutubeVideo;

import java.io.*;
import java.util.ArrayList;

public class YoutubeVideoUtil {

    private static final int VIDEO_CODE_LENGTH = 11;
    private static final int PLAYLIST_CODE_LENGTH = 34;

    public static void main(String[] args) {
        YoutubeVideo video1 = retrieveVideoFromUrl("https://www.youtube.com/watch?v=9oegSW-z4Fc");
        System.out.println(video1);
        YoutubeVideo[] youtubeVideos = retrieveVideosFromPlaylistUrl("https://www.youtube.com/playlist?list=PL7gxY-4Ay7vfRoWJNwcb6omsajXGZpCSY");
        assert youtubeVideos != null : "Error";
        for (YoutubeVideo video : youtubeVideos) {
            System.out.println(video);
        }
    }

    public static YoutubeVideo[] retrieveVideosFromPlaylistUrl(String url) {
        if (!isPlaylist(url)) {
            return null;
        }
        try {
            String playlistCode = retrievePlaylistCodeFromUrL(url);
            Process cmd = Runtime.getRuntime().exec("cmd");
            new Thread(new DownloadSyncPipe(cmd.getInputStream(), System.out, null)).start();
            new Thread(new DownloadSyncPipe(cmd.getErrorStream(), System.err, null)).start();
            PrintWriter printWriter = new PrintWriter(cmd.getOutputStream());
            printWriter.println(Settings.get(Settings.YOUTUBE_DL_PATH) + " -o \"videos/%(id)s\" \"" + url + "\" --skip-download --write-info-json");
            printWriter.close();
            System.out.println("Retrieving videos");
            int i = cmd.waitFor();
            System.out.println("Videos Retrieved");
            ArrayList<YoutubeVideo> videos = new ArrayList<>();
            if (i == 0) {
                File dir = new File("videos");
                if (dir.exists() && dir.listFiles() != null) {
                    Gson gson = new Gson();
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.getName().contains(playlistCode)) {
                            boolean delete = file.delete();
                            System.out.println(file.getName() + ":\t" + delete);
                            continue;
                        }
                        YoutubeVideo e = new YoutubeVideo();
                        FileReader json = new FileReader(file);
                        e.applyInfo(gson.fromJson(json, InfoYoutubeVideoFile.class));
                        videos.add(e);
                        json.close();
                        boolean delete = file.delete();
                        System.out.println(file.getName() + ":\t" + delete);
                    }
                    boolean delete = dir.delete();
                    System.out.println(dir.getName() + ":\t" + delete);
                    return videos.toArray(new YoutubeVideo[]{});
                } else {
                    System.out.println("Passed an empty playlist");
                    return null;
                }
            } else {
                System.out.println("Can't retrieve video from playlist");
                return null;
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static YoutubeVideo retrieveVideoFromUrl(String url) {
        if (!isVideo(url))
            return null;
        YoutubeVideo video = new YoutubeVideo(retrieveVideoCodeFromUrL(url));
        try {
            Process cmd = Runtime.getRuntime().exec("cmd");
            PrintWriter printWriter = new PrintWriter(cmd.getOutputStream());
            printWriter.println(Settings.get(Settings.YOUTUBE_DL_PATH) + " -o \"" + video.getCode() + "\" \"" + url + "\" --skip-download --write-info-json");
            printWriter.close();
            int i = cmd.waitFor();
            if (i == 0) {
                File infoFile = new File(video.getCode() + ".info.json");
                if (infoFile.exists()) {
                    Gson gson = new Gson();
                    FileReader json = new FileReader(infoFile);
                    InfoYoutubeVideoFile info = gson.fromJson(json, InfoYoutubeVideoFile.class);
                    video.applyInfo(info);
                    json.close();
                    boolean delete = infoFile.delete();
                    System.out.println(infoFile.getName() + ":\t" + delete);
                } else {
                    System.out.println("Can't create info file");
                }
            } else {
                System.out.println("Error While retrieving json info");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return video;
    }

    public static boolean isPlaylist(String url) {
        return url.contains("playlist");
    }

    public static boolean isVideo(String url) {
        return url.contains("watch") || url.length() == VIDEO_CODE_LENGTH;
    }

    public static String retrievePlaylistCodeFromUrL(String url) {
        if (!url.startsWith("https"))
            return url;
        int index = url.indexOf("playlist?list=");
        if (index == -1)
            throw new IllegalArgumentException("Invalid youtube playlist url: " + url);
        return url.substring(index + 14, index + 14 + PLAYLIST_CODE_LENGTH);
    }

    public static String retrieveVideoCodeFromUrL(String url) {
        if (!url.startsWith("https"))
            return url;
        int index = url.indexOf("watch?v=");
        if (index == -1)
            throw new IllegalArgumentException("Invalid youtube video url: " + url);
        return url.substring(index + 8, index + 8 + VIDEO_CODE_LENGTH);
    }
}
