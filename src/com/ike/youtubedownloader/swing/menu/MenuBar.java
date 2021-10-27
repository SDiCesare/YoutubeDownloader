package com.ike.youtubedownloader.swing.menu;

import com.ike.youtubedownloader.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Ike
 * @version 1.0A
 **/
public class MenuBar extends JMenuBar {

    public MenuBar() {
        super();
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
