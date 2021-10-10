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
        downloadItem.addActionListener((e) -> Frame.downloader.download(this.video));
        JMenuItem openVideo = new JMenuItem("Open in Browser");
        openVideo.addActionListener((e) -> {
            try {
                Desktop.getDesktop().browse(new URI(this.video.getURL()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        JMenuItem modifyTag = new JMenuItem("Modify Tags");
        modifyTag.addActionListener((e) -> {
            System.out.println("Modify Tags");
        });
        this.add(downloadItem);
        this.addSeparator();
        this.add(openVideo);
        this.addSeparator();
        this.add(modifyTag);
    }

}
