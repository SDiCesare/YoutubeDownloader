package com.ike.youtubedownloader.swing.panel;

import com.ike.youtubedownloader.swing.Frame;
import com.ike.youtubedownloader.util.SongFilter;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.LinkedList;

public class FilterPanel extends JPanel {

    private LinkedList<YoutubeVideo> videos;
    private LinkedList<YoutubeVideo> showedVideos;
    private LinkedList<JCheckBox> boxes;
    private LinkedList<SongFilter> filters;

    public FilterPanel(LinkedList<YoutubeVideo> videos) {
        super();
        this.setBackground(Color.BLUE);
        Dimension size = new Dimension(Frame.WIDTH - 20, 30);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setLayout(null);
        this.boxes = new LinkedList<>();
        this.filters = new LinkedList<>();
        this.videos = videos;
        this.addBox("In downloading", list -> new LinkedList<>());
    }

    public void addBox(String name, SongFilter filter) {
        JCheckBox box = new JCheckBox(name);
        int x = this.boxes.size() == 0 ? 0 : this.boxes.get(this.boxes.size() - 1).getWidth() + 5;
        box.setBounds(x, 0, name.length() * 8, this.getHeight());
        box.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.showedVideos = filter.filter(videos);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                this.showedVideos = videos;
            }
        });
        this.add(box);
        this.filters.add(filter);
        this.boxes.add(box);
    }

    public LinkedList<YoutubeVideo> getVideos() {
        return videos;
    }

    public LinkedList<YoutubeVideo> getShowedVideos() {
        return showedVideos;
    }
}
