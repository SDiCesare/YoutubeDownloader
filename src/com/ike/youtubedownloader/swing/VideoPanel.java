package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Ike
 * @version 1.0A
 **/
public class VideoPanel extends JPanel {

    private YoutubeVideo video;
    private BufferedImage thumbnail;
    private JButton downloadButton;

    public VideoPanel(YoutubeVideo video) {
        super();
        this.video = video;
        this.thumbnail = this.video.getThumbnail();
        this.setBackground(new Color(200, 200, 200));
        this.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        this.downloadButton = new JButton("↧");
        this.downloadButton.setFont(this.downloadButton.getFont().deriveFont(15.f));
        this.downloadButton.setBackground(new Color(150, 250, 50));
        this.downloadButton.setBounds(400, 15, 50, 50);
        this.downloadButton.addActionListener((e) -> {
            System.out.println("Download " + this.video);
        });
        this.setLayout(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 3) {
                    JVideoOptionMenu menu = new JVideoOptionMenu(VideoPanel.this.video);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        //this.add(downloadButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int x = (int) (this.getHeight() * 1.5);
        g.drawImage(this.thumbnail, 5, 5, x, this.getHeight() - 5, null);
        g.setFont(new Font(null, Font.PLAIN, 20));
        g.drawString(video.getTitle(), x + 10, 50);
        g.setFont(g.getFont().deriveFont(10.f));
        g.drawString(video.getAuthor(), x + 10, 65);
    }
}
