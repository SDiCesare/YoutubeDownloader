package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.stream.callback.JCallback;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;

/**
 * The Frame with the history of the downloads
 *
 * @author Ike
 * @version 1.0A
 **/
public class DownloadsFrame extends JFrame {

    private JPanel mainPanel;

    public DownloadsFrame() {
        super("Downloads");
        this.setSize(300, 400);
        this.setResizable(false);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        this.mainPanel.setSize(300, 1);
        JScrollPane pane = new JScrollPane(this.mainPanel);
        pane.setBounds(0, 0, 300, 400);
        this.setLayout(null);
        this.add(pane);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * Add a video to download to the history
     *
     * @param video The video to ass
     */
    public void addDownloadProcess(YoutubeVideo video) {
        JCallback callback = new JCallback(video);
        callback.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        int h = 30;
        int height = this.mainPanel.getComponentCount() * h;
        callback.setBounds(0, height, 300, h);
        this.mainPanel.setPreferredSize(new Dimension(this.mainPanel.getWidth(), height + callback.getHeight()));
        this.mainPanel.setSize(new Dimension(this.mainPanel.getWidth(), height + callback.getHeight()));
        this.mainPanel.add(callback);
        this.repaint();
        callback.start();
    }

}
