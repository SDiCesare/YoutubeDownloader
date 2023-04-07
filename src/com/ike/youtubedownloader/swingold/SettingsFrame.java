package com.ike.youtubedownloader.swingold;

import com.ike.youtubedownloader.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Ike
 * @version 1.0A
 **/
public class SettingsFrame extends JFrame {

    private JPanel generalSettings;
    private JPanel downloadSettings;
    private JPanel settingsMenu;

    public SettingsFrame() {
        super("Settings");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setSize(746, 429);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(null);
        Rectangle bounds = new Rectangle(200, 0, 530, 400);
        //General Settings
        this.generalSettings = new JPanel();
        this.generalSettings.setBounds(bounds);
        this.generalSettings.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.generalSettings.setLayout(null);
        DescriptionTextField dll = new DescriptionTextField("Youtube-Dl Path: ");
        dll.setBounds(0, 20, 530, 40);
        dll.setFieldValue(Settings.get(Settings.YOUTUBE_DL_PATH));
        this.generalSettings.add(dll);
        //Download Settings
        this.downloadSettings = new JPanel();
        this.downloadSettings.setBounds(bounds);
        this.downloadSettings.setLayout(null);
        this.downloadSettings.setBorder(BorderFactory.createLineBorder(Color.RED));
        DescriptionTextField outPath = new DescriptionTextField("Downloads Path:");
        outPath.setBounds(0, 20, 530, 40);
        outPath.setFieldValue(Settings.get(Settings.DOWNLOAD_DIR));
        JCheckBox directDownload = new JCheckBox("Directly Download Research");
        directDownload.setSelected(Boolean.parseBoolean(Settings.get(Settings.DIRECT_DOWNLOAD)));
        directDownload.setBounds(0, 70, 530, 40);
        directDownload.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLACK));
        directDownload.setBorderPainted(true);
        JCheckBox saveArtist = new JCheckBox("Save in Artist Directory");
        saveArtist.setBorderPainted(true);
        saveArtist.setSelected(Boolean.parseBoolean(Settings.get(Settings.SAVE_ARTIST)));
        saveArtist.setBounds(0, 120, 530, 40);
        saveArtist.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLACK));
        DescriptionTextField results = new DescriptionTextField("Number of Max Results:");
        results.setBounds(0, 170, 530, 40);
        results.setFieldValue(Settings.get(Settings.RESULTS));
        this.downloadSettings.add(outPath);
        this.downloadSettings.add(directDownload);
        this.downloadSettings.add(saveArtist);
        this.downloadSettings.add(results);
        //Settings Menu
        this.settingsMenu = new JPanel();
        this.settingsMenu.setBounds(0, 0, 200, 400);
        this.settingsMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.settingsMenu.setLayout(null);
        addSettingMenu("General Settings", this.generalSettings);
        addSettingMenu("Download Settings", this.downloadSettings);
        JButton applyChanges = new JButton("Apply Changes");
        applyChanges.setBounds(25, 340, 150, 40);
        applyChanges.addActionListener((e) -> {
            Settings.change(Settings.YOUTUBE_DL_PATH, dll.getFieldValue());
            Settings.change(Settings.DOWNLOAD_DIR, outPath.getFieldValue());
            Settings.change(Settings.RESULTS, results.getFieldValue());
            Settings.change(Settings.DIRECT_DOWNLOAD, String.valueOf(directDownload.isSelected()));
            Settings.change(Settings.SAVE_ARTIST, String.valueOf(saveArtist.isSelected()));
        });
        this.settingsMenu.add(applyChanges);
        /*new Thread(() -> {
            while(true) {
                try {
                    System.out.println(this.getSize());
                    Thread.sleep(1000L);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();*/
    }

    public void init() {
        this.getContentPane().removeAll();
        this.getContentPane().add(this.settingsMenu);
        this.getContentPane().add(this.generalSettings);
        this.setVisible(true);
    }

    private void show(JPanel setting) {
        if (!this.isVisible())
            this.setVisible(true);
        this.getContentPane().removeAll();
        this.getContentPane().add(this.settingsMenu);
        this.getContentPane().add(setting);
        this.repaint();
    }

    private void addSettingMenu(String name, JPanel setting) {
        JLabel field = new JLabel(name);
        int n = this.settingsMenu.getComponentCount();
        int h = 40;
        field.setBounds(0, n * h, 300, h);
        field.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                SettingsFrame.this.show(setting);
            }
        });
        this.settingsMenu.add(field);
    }

}
