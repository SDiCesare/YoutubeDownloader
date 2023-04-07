package com.ike.youtubedownloader.swing.panel;

import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoPanel extends JPanel {

    private YoutubeVideo video;
    private BufferedImage image;

    private JLabel nameLabel;
    private JLabel downloadProgress;

    public VideoPanel(YoutubeVideo video, int width, int height) {
        this.video = video;
        this.image = video.getThumbnail();
        this.setLayout(null);
        this.nameLabel = new JLabel(video.getAuthor() + " - " + video.getTitle());
        this.nameLabel.setBounds(100, 10, width - 150, height - 10);
        this.downloadProgress = new JLabel("100%");
        this.downloadProgress.setBounds(width - 50, 10, 50, height - 10);
        this.add(this.nameLabel);
        this.add(this.downloadProgress);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(this.image, 10, 10, 100, 75, null);
    }
}
