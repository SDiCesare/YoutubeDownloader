package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.stream.Searcher;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Frame extends JFrame {

    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);
    }


    private HintTextField searchField;
    private JButton searchButton;
    private SearchPanel searchPanel;
    private final ActionListener downloadAction = (e) -> new Thread(() -> {
        this.searchPanel.resetVideos();
        String text = this.searchField.getText();
        System.out.println("Searching for: " + text);
        ArrayList<YoutubeVideo> search = Searcher.search(text, 3);
        for (YoutubeVideo video : search) {
            this.searchPanel.addVideo(video);
        }
        Frame.this.repaint();
    }).start();

    public Frame() {
        super("Youtube Downloader");
        this.setSize(700, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.searchField = new HintTextField("Search a Video");
        this.searchField.setBounds(10, 15, 550, 50);
        this.searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    downloadAction.actionPerformed(null);
                }
            }
        });
        this.searchButton = new JButton("Search");
        this.searchButton.setBounds(570, 15, 100, 50);
        this.searchButton.addActionListener(downloadAction);
        this.searchPanel = new SearchPanel();
        this.searchPanel.setBounds(10, 100, 670, 300);
        this.add(this.searchField);
        this.add(this.searchButton);
        this.add(this.searchPanel);
    }

}
