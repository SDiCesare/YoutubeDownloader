package com.ike.youtubedownloader.swingold.panels;

import com.ike.youtubedownloader.swingold.HintTextField;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.util.ArrayList;

public class SearchPanel extends JPanel {

    private ArrayList<YoutubeVideo> videos;

    private HintTextField search;

    public SearchPanel() {
        super();
        this.videos = new ArrayList<>();
        this.search = new HintTextField("Search Video Here");
        this.search.setBounds(0, 0, 100, 50);
    }

}
