package com.ike.youtubedownloader;

import com.ike.youtubedownloader.stream.Downloader;
import com.ike.youtubedownloader.video.YoutubeVideo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Main {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.setDllPath("D:\\Musica\\DLL");
        downloader.setOutPath("D:\\Musica\\Download");
        ArrayList<YoutubeVideo> youtubeVideos = searchYoutubeVideos("https://www.youtube.com/playlist?list=PL7gxY-4Ay7vevT1ixQRu0sd9EHHg9YvOX");
        for (YoutubeVideo video : youtubeVideos) {
            downloader.download(video);
        }
    }

    private static ArrayList<YoutubeVideo> searchYoutubeVideos(String... urls) {
        return searchYoutubeVideos(0, urls);
    }

    private static ArrayList<YoutubeVideo> searchYoutubeVideos(int n, String... urls) {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();
        for (String stringUrl : urls) {
            try {
                if (stringUrl.contains("watch?v=")) {
                    URL url = new URL(stringUrl);
                    videos.add(getVideoFromURL(stringUrl.substring(stringUrl.indexOf("=") + 1), getPageContent(url)));
                } else if (stringUrl.contains("playlist?list")) {
                    URL url = new URL(stringUrl);
                    videos.addAll(getCodes(getPageContent(url), n));
                } else {
                    URL url = new URL("https://www.youtube.com/results?search_query=" + stringUrl.replace(" ", "+"));
                    videos.addAll(getCodes(getPageContent(url), n));
                }
            } catch (IOException ex) {
                System.out.println("Error Occurred While download " + stringUrl);
                ex.printStackTrace();
            }
        }
        return videos;
    }

    private static StringBuilder getPageContent(URL url) throws IOException {
        InputStreamReader r = new InputStreamReader(url.openStream());
        BufferedReader reader = new BufferedReader(r);
        StringBuilder text = new StringBuilder();
        String ln = reader.readLine();
        while (ln != null) {
            text.append(ln).append("\n");
            ln = reader.readLine();
        }
        return text;
    }

    private static YoutubeVideo getVideoFromURL(String code, StringBuilder text) {
        YoutubeVideo video = new YoutubeVideo(code);
        //Title
        String titleStr = "href=\"https://m.youtube.com/watch?v=" + code + "\"><title>";
        int titleIndex = text.indexOf(titleStr) + titleStr.length();
        titleStr = text.substring(titleIndex);
        titleStr = titleStr.substring(0, titleStr.indexOf("</title>")).replace(" - YouTube", "");
        video.setTitle(titleStr);
        //Author
        String authorStr = "/channel\\";
        int authorIndex = text.indexOf(authorStr) + authorStr.length();
        if (authorIndex == authorStr.length() - 1) {
            authorStr = "/user\\";
            authorIndex = text.indexOf(authorStr) + authorStr.length();
        }
        String author = text.substring(authorIndex);
        authorStr = author.substring(author.indexOf("name") + 8);
        authorStr = authorStr.substring(0, authorStr.indexOf("}}]") - 1);
        video.setAuthor(authorStr);
        return video;
    }

    private static ArrayList<YoutubeVideo> getCodes(StringBuilder text, int n) throws IOException {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();
        while (true) {
            int videoID = text.indexOf("/watch?v=");
            if (videoID == -1)
                break;
            String code = text.substring(videoID + 9, videoID + 9 + 11);
            text = new StringBuilder(text.substring(videoID + 9));
            URL url = new URL("https://www.youtube.com/watch?v=" + code);
            YoutubeVideo video = getVideoFromURL(code, getPageContent(url));
            if (!videos.contains(video)) {
                videos.add(video);
                if (videos.size() == n) {
                    return videos;
                }
            }
        }
        return videos;
    }

}
