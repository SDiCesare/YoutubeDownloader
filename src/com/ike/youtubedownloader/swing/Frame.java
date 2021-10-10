package com.ike.youtubedownloader.swing;

import javax.swing.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Frame extends JFrame {

    public static void main(String[] args) {
        new Frame().setVisible(true);
    }

    public Frame() {
        super("Youtube Downloader");
        this.setSize(500, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

}
