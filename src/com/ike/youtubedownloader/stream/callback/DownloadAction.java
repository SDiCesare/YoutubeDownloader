package com.ike.youtubedownloader.stream.callback;

import com.ike.youtubedownloader.video.YoutubeVideo;

public interface DownloadAction {

    void perform(YoutubeVideo video);

}
