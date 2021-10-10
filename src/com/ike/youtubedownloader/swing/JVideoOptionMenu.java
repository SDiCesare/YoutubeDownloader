package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ike
 * @version 1.0A
 **/
public class JVideoOptionMenu extends JPopupMenu {

    private YoutubeVideo video;

    public JVideoOptionMenu(YoutubeVideo video) {
        super();
        this.video = video;
        JMenuItem downloadItem = new JMenuItem("Download");
        downloadItem.addActionListener((e) -> {
            System.out.println("Download: " + this.video);
        });
        JMenuItem openVideo = new JMenuItem("Open in Browser");
        openVideo.addActionListener((e) -> {
            try {
                Desktop.getDesktop().browse(new URI(this.video.getURL()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        this.add(downloadItem);
        this.addSeparator();
        this.add(openVideo);
    }

}
