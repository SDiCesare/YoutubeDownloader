package com.ike.youtubedownloader.swing.menu;

import com.ike.youtubedownloader.swing.Frame;
import com.ike.youtubedownloader.util.Settings;
import com.ike.youtubedownloader.video.VideoFile;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class MenuBar extends JMenuBar {

    public MenuBar() {
        super();
        //File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveToFile = new JMenuItem("Save Current");
        saveToFile.addActionListener((e) -> {
            JFileChooser chooser = new JFileChooser();
            int i = chooser.showOpenDialog(Frame.frame);
            if (i == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                VideoFile.saveOnFile(selectedFile, Frame.frame.getVideos());
            }
        });
        JMenuItem openFromFile = new JMenuItem("Open from File");
        openFromFile.addActionListener((e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("YoutubeVideo File", "ytv"));
            int i = chooser.showOpenDialog(Frame.frame);
            if (i == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                ArrayList<YoutubeVideo> videos = VideoFile.loadFromFile(selectedFile);
                Frame.frame.setVideos(videos);
            }
        });
        fileMenu.add(openFromFile);
        fileMenu.addSeparator();
        fileMenu.add(saveToFile);
        this.add(fileMenu);
        //Settings Menu
        JMenu settings = new JMenu("Settings");
        JCheckBoxMenuItem directDownload = new JCheckBoxMenuItem("Direct Download");
        directDownload.setState(Boolean.parseBoolean(Settings.get(Settings.DIRECT_DOWNLOAD)));
        directDownload.addActionListener((e) -> Settings.change(Settings.DIRECT_DOWNLOAD, String.valueOf(directDownload.getState())));
        JCheckBoxMenuItem saveArtist = new JCheckBoxMenuItem("Save on artist folder");
        saveArtist.setState(Boolean.parseBoolean(Settings.get(Settings.SAVE_ARTIST)));
        saveArtist.addActionListener((e) -> Settings.change(Settings.SAVE_ARTIST, String.valueOf(saveArtist.getState())));
        JMenu resultMenu = new JMenu("Results");
        JTextField results = new JTextField(Settings.get(Settings.RESULTS));
        results.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Settings.change(Settings.RESULTS, results.getText());
            }
        });
        results.setMinimumSize(new Dimension(50, 30));
        resultMenu.add(results);
        settings.add(directDownload);
        settings.add(saveArtist);
        settings.add(resultMenu);
        this.add(settings);
    }

}
