package com.ike.youtubedownloader.video;

import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author Ike
 * @version 1.0A
 **/
public class YoutubeVideo {

    private String author;
    private String title;
    private String code;
    private String thumbnailUrl;

    public YoutubeVideo(String code) {
        this.code = code;
        this.thumbnailUrl = "https://i.ytimg.com/vi/" + code + "/hq720.jpg";
    }

    public YoutubeVideo() {

    }

    public BufferedImage getThumbnail() {
        try {
            return ImageIO.read(new URL(this.thumbnailUrl));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Jsoup.parse(author).text();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Jsoup.parse(title).text();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getURL() {
        return "https://www.youtube.com/watch?v=" + this.code;
    }

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(s.lastIndexOf(".") + 1);
        s += "[code=" + this.code + ",author=" + this.author + ",title=" + this.title + "]";
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof YoutubeVideo)
            return this.code.equals(((YoutubeVideo) obj).code);
        return super.equals(obj);
    }
}