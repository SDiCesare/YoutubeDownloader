package com.ike.youtubedownloader.swingold;

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

    public VideoPanel(YoutubeVideo video) {
        super();
        this.video = video;
        this.thumbnail = this.video.getThumbnail();
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 3) {
                    JVideoOptionMenu menu = new JVideoOptionMenu(VideoPanel.this.video);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int x = (int) (this.getHeight() * 1.5);
        g.drawImage(this.thumbnail, 5, 5, x, this.getHeight() - 10, null);
        g.setFont(new Font(null, Font.PLAIN, 20));
        if (video.getTitle() != null)
            g.drawString(video.getTitle(), x + 10, 50);
        g.setFont(g.getFont().deriveFont(15.f));
        if (video.getAuthor() != null)
            g.drawString(video.getAuthor(), x + 10, 65);
    }

    public YoutubeVideo getVideo() {
        return video;
    }
}
