package com.ike.youtubedownloader.swing;

import com.ike.youtubedownloader.stream.Downloader;
import com.ike.youtubedownloader.stream.Searcher;
import com.ike.youtubedownloader.swing.menu.MenuBar;
import com.ike.youtubedownloader.util.Settings;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.event.*;
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
        if (Settings.get(Settings.DIRECT_DOWNLOAD).equals("true")) {
            ArrayList<YoutubeVideo> search = Searcher.search(text, 1);
            if (search.size() > 0) {
                YoutubeVideo video = search.get(0);
                this.searchPanel.addVideo(video);
                this.download(video);
                this.actionLabel.setText("Downloading " + video.toString());
            } else {
                this.actionLabel.setText("Video/s not Found");
            }
            return;
        }
        ArrayList<YoutubeVideo> search = Searcher.search(text, Integer.parseInt(Settings.get(Settings.RESULTS)));
        setVideos(search);
    }).start();
    private JLabel actionLabel;
    private HintTextField titleField;
    private HintTextField artistField;
    private JButton saveTag;
    private JButton downloadActive;
    private JButton downloadAll;
    private YoutubeVideo activeVideo;

    public Frame() {
        super("Youtube Downloader");
        this.setSize(700, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Settings.save();
            }
        });
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
        this.saveTag = new JButton("Save Tag");
        this.saveTag.setBounds(100, 600, 100, 50);
        this.saveTag.addActionListener((e) -> {
            if (this.activeVideo == null) {
                this.actionLabel.setText("Can't Download a null video");
                System.out.println("Can't Download a null video");
            } else {
                this.activeVideo.setAuthor(this.artistField.getText());
                this.activeVideo.setTitle(this.titleField.getText());
                this.repaint();
            }
        });
        this.downloadActive = new JButton("Download");
        this.downloadActive.setBounds(250, 600, 100, 50);
        this.downloadActive.addActionListener((e) -> new Thread(() -> {
            if (this.activeVideo == null) {
                this.actionLabel.setText("Can't Download a null video");
                System.out.println("Can't Download a null video");
            } else {
                this.activeVideo.setAuthor(this.artistField.getText());
                this.activeVideo.setTitle(this.titleField.getText());
                this.actionLabel.setText("Downloading " + this.activeVideo);
                this.download(this.activeVideo);
            }
        }).start());
        this.downloadAll = new JButton("Download All");
        this.downloadAll.setBounds(400, 600, 120, 50);
        this.downloadAll.addActionListener((e) -> {
            ArrayList<YoutubeVideo> videos = this.getVideos();
            this.actionLabel.setText("Downloading " + videos.size() + " videos");
            for (YoutubeVideo video : videos) {
                this.download(video);
            }
        });
        this.add(this.artistField);
        this.add(this.titleField);
        this.add(this.saveTag);
        this.add(this.downloadActive);
        this.add(this.downloadAll);
    }

    public ArrayList<YoutubeVideo> getVideos() {
        return this.searchPanel.getVideos();
    }

    public void setVideos(ArrayList<YoutubeVideo> videos) {
        for (YoutubeVideo video : videos) {
            this.searchPanel.addVideo(video);
        }
        if (videos.size() > 0) {
            this.actionLabel.setText("Found: " + videos.size() + " videos");
        } else {
            this.actionLabel.setText("Video/s not Found");
        }
        Frame.this.repaint();
        this.titleField.setEnabled(false);
        this.artistField.setEnabled(false);
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
