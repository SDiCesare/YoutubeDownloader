package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class SearchPanel extends JPanel {

    private JPanel videosPanel;
    private JScrollPane pane;

    public SearchPanel() {
        this.setBackground(Color.BLACK);
        this.videosPanel = new JPanel();
        this.videosPanel.setSize(new Dimension(670, 0));
        this.videosPanel.setLayout(null);
        this.pane = new JScrollPane(this.videosPanel);
        this.pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.pane.getVerticalScrollBar().setUnitIncrement(5);
        this.setLayout(null);
        this.add(pane);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        pane.setBounds(0, 0, width, height);
    }

    public void resetVideos() {
        Component[] components = this.videosPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof VideoPanel) {
                this.videosPanel.remove(components[i]);
            }
        }
        this.videosPanel.setSize(this.videosPanel.getWidth(), 0);
    }

    public void addVideo(YoutubeVideo video) {
        VideoPanel panel = new VideoPanel(video);
        int h = this.videosPanel.getComponentCount() * 100;
        panel.setBounds(0, h, this.videosPanel.getWidth(), 100);
        Dimension size = this.videosPanel.getSize();
        this.videosPanel.setPreferredSize(new Dimension(size.width, size.height + panel.getHeight()));
        this.videosPanel.setSize(new Dimension(size.width, size.height + panel.getHeight()));
        this.videosPanel.add(panel);
    }

    public ArrayList<YoutubeVideo> getVideos() {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();
        for (Component c : this.videosPanel.getComponents()) {
            if (c instanceof VideoPanel) {
                videos.add(((VideoPanel) c).getVideo());
            }
        }
        return videos;
    }

}
