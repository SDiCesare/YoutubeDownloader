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
        JMenuItem changeSettings = new JMenuItem("Change Settings");
        changeSettings.addActionListener((e) -> Frame.frame.openSettings());
        settings.add(changeSettings);
        this.add(settings);
    }

}
