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

    public static YoutubeVideo fromJson(String json) {
        //Format: {code=xxx,author=xxx,title=xxx}
        json = json.substring(1, json.length() - 1);
        String[] vars = json.split(",");
        if (!vars[0].contains("code")) {
            throw new IllegalArgumentException("Invalid Json format: Code Missing!");
        }
        //Code
        String[] code = vars[0].split("=");
        YoutubeVideo video = new YoutubeVideo(code[1]);
        if (vars.length > 1) {
            if (vars[1].contains("author")) {
                String[] author = vars[1].split("=");
                video.author = author[1];
            } else if (vars[1].contains("title")) {
                String[] title = vars[1].split("=");
                video.title = title[1];
            }
        }
        if (vars.length > 2) {
            if (vars[2].contains("author")) {
                String[] author = vars[2].split("=");
                video.author = author[1];
            } else if (vars[2].contains("title")) {
                String[] title = vars[2].split("=");
                video.title = title[1];
            }
        }
        return video;
    }

    private String author;
    private String title;
    private String code;
    private String thumbnailUrl;

    public YoutubeVideo(String code) {
        this.code = code;
        this.thumbnailUrl = "https://i.ytimg.com/vi/" + code + "/hqdefault.jpg";
    }

    public YoutubeVideo() {

    }

    public String toJson() {
        StringBuilder out = new StringBuilder();
        //{code=xxx,author=xxx,title=xxx}
        out.append("{").append("code=").append(this.code);
        if (this.author != null) {
            out.append(",author=").append(this.author);
        }
        if (this.title != null) {
            out.append(",title=").append(this.title);
        }
        out.append("}");
        return out.toString();
    }

    public BufferedImage getThumbnail() {
        try {
            return ImageIO.read(new URL(this.thumbnailUrl));
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.err.println("Can't get thumbnail " + this.thumbnailUrl + " for video code " + this.code);
            return new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
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