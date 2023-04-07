package com.ike.youtubedownloader.stream;

import com.ike.youtubedownloader.util.YoutubeVideoUtil;
import com.ike.youtubedownloader.video.YoutubeVideo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * The class that manages the Researches
 *
 * @author Ike
 * @version 1.0A
 **/
public class Searcher {

    /**
     * Search a list of YoutubeVideos by the research
     *
     * @param research The text to search
     * @param n        The max number of results. set 0 for the max possible results given by Youtube
     * @return A list of YoutubeVideos result from the research
     */
    public static ArrayList<YoutubeVideo> search(String research, int n) {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();
        try {
            if (YoutubeVideoUtil.isVideo(research)) {
                URL url = new URL(research);
                YoutubeVideo videoFromURL = getVideoFromURL(YoutubeVideoUtil.retrieveVideoCodeFromUrL(research), getPageContent(url));
                videos.add(videoFromURL);
            } else if (YoutubeVideoUtil.isPlaylist(research)) {
                URL url = new URL(research);
                videos.addAll(getCodes(getPageContent(url), 0));
            } else {
                URL url = new URL("https://www.youtube.com/results?search_query=" + research.replace(" ", "+"));
                videos.addAll(getCodes(getPageContent(url), n));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return videos;
    }

    /**
     * @param url The url of a html page
     * @return The HTML content of a page
     */
    private static StringBuilder getPageContent(URL url) throws IOException {
        InputStreamReader r = new InputStreamReader(url.openStream());
        BufferedReader reader = new BufferedReader(r);
        StringBuilder text = new StringBuilder();
        String ln = reader.readLine();
        while (ln != null) {
            text.append(ln).append("\n");
            ln = reader.readLine();
        }
        System.out.println(text);
        System.exit(0);
        return text;
    }

    /**
     * Get a {@link YoutubeVideo} with his tags by the html source and his code
     *
     * @param code The unique video code
     * @param text The HTML text of the youtube page
     * @return The YoutubeVideo of the specified code
     */
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


    /**
     * Get an n-length list of YoutubeVideo from an HTML source
     *
     * @param text The html source
     * @param n    The max length of the YoutubeVideo list
     * @return a n-length YoutubeVideo list
     */
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
                if (n != 0 && videos.size() == n) {
                    return videos;
                }
            }
        }
        return videos;
    }

}
