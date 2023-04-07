package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.stream.Downloader;
import com.ike.youtubedownloader.swing.panel.ActionPanel;
import com.ike.youtubedownloader.swing.panel.DownloadsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame extends JFrame {

    public static final int WIDTH = 910;
    public static final int HEIGHT = 600;

    private Downloader downloader;

    private ActionPanel actionPanel;
    private DownloadsPanel downloadsPanel;


    public Frame() {
        super("Youtube MP3 Downloader");
        initLayout();
        this.downloader = new Downloader();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println(getContentPane().getSize());
            }
        });
    }

    private void initLayout() {
        this.actionPanel = new ActionPanel();
        this.actionPanel.setBounds(0, 0, WIDTH, 60);
        this.actionPanel.setBackground(Color.RED);
        this.actionPanel.setDownloadAction((video) -> {
            this.downloadsPanel.addSong(video);
        });
        this.downloadsPanel = new DownloadsPanel();
        this.downloadsPanel.setBounds(10, 60, WIDTH - 20, HEIGHT - 70);
        this.downloadsPanel.setBackground(Color.GREEN);
        Container contentPane = this.getContentPane();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        contentPane.setMinimumSize(size);
        contentPane.setMaximumSize(size);
        contentPane.setPreferredSize(size);
        contentPane.setLayout(null);
        contentPane.add(this.actionPanel);
        contentPane.add(this.downloadsPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            this.pack();
            this.setResizable(false);
            this.setLocationRelativeTo(null);
        }
    }
}
