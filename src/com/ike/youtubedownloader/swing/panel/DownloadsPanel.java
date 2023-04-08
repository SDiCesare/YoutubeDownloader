package com.ike.youtubedownloader.swing.panel;

import com.ike.youtubedownloader.stream.callback.DownloadCallback;
import com.ike.youtubedownloader.swing.Frame;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class DownloadsPanel extends JPanel {

    private FilterPanel filterPanel;
    private JScrollPane scrollPane;
    private SongView songsPanel;

    public DownloadsPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.filterPanel = new FilterPanel(null);
        this.songsPanel = new SongView();
        this.scrollPane = new JScrollPane(songsPanel);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.add(this.filterPanel, BorderLayout.NORTH);
    }

    public DownloadCallback addSong(YoutubeVideo video) {
        DownloadCallback downloadCallback = this.songsPanel.addSong(video);
        this.repaint();
        return downloadCallback;
    }


    private class SongView extends JPanel {

        private static final int WIDTH = Frame.WIDTH - 38;
        private static final int HEIGHT = 100;

        private LinkedList<SongPanel> panels;

        public SongView() {
            super();
            this.setLayout(null);
            this.panels = new LinkedList<>();
        }

        private DownloadCallback addSong(YoutubeVideo video) {
            SongPanel songPanel = new SongPanel(video, WIDTH, HEIGHT);
            songPanel.setLocation(0, this.panels.size() * HEIGHT);
            this.panels.add(songPanel);
            this.add(songPanel);
            Dimension size = new Dimension(WIDTH, this.panels.size() * HEIGHT);
            this.setPreferredSize(size);
            this.setSize(size);
            this.setMaximumSize(size);
            this.setMinimumSize(size);
            return songPanel;
        }

    }

}
