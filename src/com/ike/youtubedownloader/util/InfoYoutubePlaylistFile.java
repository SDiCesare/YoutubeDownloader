package com.ike.youtubedownloader.util;

public class InfoYoutubePlaylistFile {

    private String id;
    private String title;
    private int entry;

    public InfoYoutubePlaylistFile() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "InfoYoutubeFile{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", entry='" + entry + '\'' +
                '}';
    }
}
