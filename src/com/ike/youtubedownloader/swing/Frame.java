package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.stream.Downloader;
import com.ike.youtubedownloader.stream.Searcher;
import com.ike.youtubedownloader.swing.menu.MenuBar;
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

    public static Downloader downloader;
    public static Frame frame;

    public static void main(String[] args) {
        frame = new Frame();
        frame.setJMenuBar(new MenuBar());
        downloader = new Downloader();
        downloader.setDllPath("D:\\Musica\\DLL");
        downloader.setOutPath("D:\\Musica\\Download");
        frame.setVisible(true);
    }

    private DownloadsFrame downloads;
    private HintTextField searchField;
    private JButton searchButton;
    private SearchPanel searchPanel;
    private final ActionListener searchAction = (e) -> new Thread(() -> {
        this.searchPanel.resetVideos();
        this.activeVideo = null;
        this.titleField.setEnabled(false);
        this.artistField.setEnabled(false);
        String text = this.searchField.getText();
        if (text == null || text.equals("")) {
            this.actionLabel.setText("Can't search an empty String");
            return;
        }
        this.actionLabel.setText("Searching for: " + text);
        System.out.println("Searching for: " + text);
        ArrayList<YoutubeVideo> search = Searcher.search(text, 3);
        for (YoutubeVideo video : search) {
            this.searchPanel.addVideo(video);
        }
        this.actionLabel.setText("Found: " + search.size() + " videos");
        Frame.this.repaint();
        this.titleField.setEnabled(false);
        this.artistField.setEnabled(false);
    }).start();
    private JLabel actionLabel;
    private HintTextField titleField;
    private HintTextField artistField;
    private JButton downloadActive;
    private YoutubeVideo activeVideo;

    public Frame() {
        super("Youtube Downloader");
        this.setSize(700, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.downloads = new DownloadsFrame();
        this.searchField = new HintTextField("Search a Video");
        this.searchField.setBounds(10, 15, 550, 50);
        this.searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchAction.actionPerformed(null);
                }
            }
        });
        this.searchButton = new JButton("Search");
        this.searchButton.setBounds(570, 15, 100, 50);
        this.searchButton.addActionListener(searchAction);
        this.searchPanel = new SearchPanel();
        this.searchPanel.setBounds(10, 100, 670, 300);
        this.add(this.searchField);
        this.add(this.searchButton);
        this.add(this.searchPanel);
        this.actionLabel = new JLabel();
        this.actionLabel.setBounds(10, 410, 670, 30);
        this.add(this.actionLabel);
        //YoutubeVideo Fields
        this.artistField = new HintTextField("Artist");
        this.artistField.setBounds(10, 450, 670, 50);
        this.titleField = new HintTextField("Title");
        this.titleField.setBounds(10, 520, 670, 50);
        this.artistField.setEnabled(false);
        this.titleField.setEnabled(false);
        this.downloadActive = new JButton("Download");
        this.downloadActive.setBounds(300, 600, 100, 50);
        this.downloadActive.addActionListener((e) -> {
            new Thread(() -> {
                if (this.activeVideo == null) {
                    this.actionLabel.setText("Can't Download a null video");
                    System.out.println("Can't Download a null video");
                } else {
                    this.activeVideo.setAuthor(this.artistField.getText());
                    this.activeVideo.setTitle(this.titleField.getText());
                    this.actionLabel.setText("Downloading " + this.activeVideo);
                    this.download(this.activeVideo);
                }
            }).start();
        });
        this.add(this.artistField);
        this.add(this.titleField);
        this.add(this.downloadActive);
    }

    public void download(YoutubeVideo video) {
        if (!this.downloads.isVisible()) {
            this.downloads.setVisible(true);
            this.downloads.setLocationRelativeTo(this);
        }
        this.downloads.addDownloadProcess(video);
    }

    public void activeVideo(YoutubeVideo video) {
        this.actionLabel.setText("Modify tag for video: " + video.getURL());
        this.activeVideo = video;
        this.artistField.setText(video.getAuthor());
        this.artistField.setEnabled(true);
        this.titleField.setText(video.getTitle());
        this.titleField.setEnabled(true);
    }

}
