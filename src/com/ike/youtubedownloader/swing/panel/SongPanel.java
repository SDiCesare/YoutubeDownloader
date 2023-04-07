package com.ike.youtubedownloader.swing.panel;

import com.ike.youtubedownloader.swing.Frame;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SongPanel extends JPanel {


    private static final Color BORDER_COLOR = new Color(170, 170, 170);
    private static final Color BACKGROUND_COLOR = new Color(220, 220, 220);

    private YoutubeVideo video;
    private BufferedImage image;

    private JLabel nameLabel;
    private JLabel downloadProgress;

    public SongPanel(YoutubeVideo video, int width, int height) {
        super();
        Dimension size = new Dimension(width, height);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setBackground(BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        this.video = video;
        this.image = video.getThumbnail();
        this.setLayout(null);
        String text = video.getAuthor() + " - " + video.getTitle();
        this.nameLabel = new JLabel(text.length() > 70 ? text.substring(0, 67) + "..." : text);
        this.nameLabel.setBounds(120, 10, width - 100, height - 20);
        this.nameLabel.setFont(this.nameLabel.getFont().deriveFont(20.0f));
        this.downloadProgress = new JLabel("100%");
        this.downloadProgress.setBounds(width - 60, 10, 50, height - 20);
        this.downloadProgress.setFont(this.downloadProgress.getFont().deriveFont(20.0f));
        this.add(this.nameLabel);
        this.add(this.downloadProgress);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(this.image, 10, 10, 100, this.getHeight() - 20, null);
    }
}
