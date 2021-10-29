package com.ike.youtubedownloader.swing;

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
        this.setSize(750, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(null);
        Rectangle bounds = new Rectangle(200, 0, 530, 400);
        //General Settings
        this.generalSettings = new JPanel();
        this.generalSettings.setBounds(bounds);
        this.generalSettings.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.generalSettings.setLayout(null);
        JLabel dllLabel = new JLabel("DLL Path:");
        dllLabel.setBounds(10, 20, 200, 40);
        JTextField dll = new JTextField(Settings.get(Settings.DLL_DIR));
        dll.setBounds(220, 20, 300, 40);
        this.generalSettings.add(dllLabel);
        this.generalSettings.add(dll);
        //Download Settings
        this.downloadSettings = new JPanel();
        this.downloadSettings.setBounds(bounds);
        this.downloadSettings.setLayout(null);
        this.downloadSettings.setBorder(BorderFactory.createLineBorder(Color.RED));
        JLabel outLabel = new JLabel("Downloads Path:");
        outLabel.setBounds(10, 20, 200, 40);
        JTextField out = new JTextField(Settings.get(Settings.DOWNLOAD_DIR));
        out.setBounds(220, 20, 300, 40);
        JCheckBox directDownload = new JCheckBox("Directly Download Research");
        directDownload.setSelected(Boolean.parseBoolean(Settings.get(Settings.DIRECT_DOWNLOAD)));
        directDownload.setBounds(10, 70, 200, 40);
        JCheckBox saveArtist = new JCheckBox("Save in Artist Directory");
        saveArtist.setSelected(Boolean.parseBoolean(Settings.get(Settings.SAVE_ARTIST)));
        saveArtist.setBounds(10, 120, 200, 40);
        JLabel resultsLabel = new JLabel("Number of Max Results:");
        resultsLabel.setBounds(10, 170, 200, 40);
        JTextField results = new JTextField(Settings.get(Settings.RESULTS));
        results.setBounds(220, 170, 300, 40);
        this.downloadSettings.add(outLabel);
        this.downloadSettings.add(out);
        this.downloadSettings.add(directDownload);
        this.downloadSettings.add(saveArtist);
        this.downloadSettings.add(resultsLabel);
        this.downloadSettings.add(results);
        //Settings Menu
        this.settingsMenu = new JPanel();
        this.settingsMenu.setBounds(0, 0, 200, 400);
        this.settingsMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.settingsMenu.setLayout(null);
        addSettingMenu("General Settings", this.generalSettings);
        addSettingMenu("Download Settings", this.downloadSettings);
        JButton applyChanges = new JButton("Apply Changes");
        applyChanges.setBounds(25, 350, 150, 40);
        applyChanges.addActionListener((e) -> {
            Settings.change(Settings.DLL_DIR, dll.getText());
            Settings.change(Settings.DOWNLOAD_DIR, out.getText());
            Settings.change(Settings.RESULTS, results.getText());
            Settings.change(Settings.DIRECT_DOWNLOAD, String.valueOf(directDownload.isSelected()));
            Settings.change(Settings.SAVE_ARTIST, String.valueOf(saveArtist.isSelected()));
        });
        this.settingsMenu.add(applyChanges);
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
