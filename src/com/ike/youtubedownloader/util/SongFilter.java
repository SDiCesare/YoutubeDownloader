package com.ike.youtubedownloader.util;

import com.ike.youtubedownloader.video.YoutubeVideo;

import java.util.LinkedList;

public interface SongFilter {

    public LinkedList<YoutubeVideo> filter(LinkedList<YoutubeVideo> list);

}
