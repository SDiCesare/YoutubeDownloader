package com.ike.youtubedownloader.swing.dialog;

import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VideoUrlDialog extends JDialog {

    private static final int DIALOG_WIDTH = 160;

    private final JTextField urlField;
    private boolean shouldDownload = false;

    public VideoUrlDialog(JFrame frame) {
        super(frame);
        this.setTitle("Insert video/playlist url");
        this.setSize(180, 230);
        JLabel titleLabel = new JLabel("Url");
        titleLabel.setBounds(10, 10, DIALOG_WIDTH, 30);
        this.urlField = new JTextField();
        this.urlField.setBounds(10, 40, DIALOG_WIDTH, 30);
        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener((e) -> {
            shouldDownload = true;
            this.dispose();
        });
        downloadButton.setBounds(10, this.urlField.getY() + 40, DIALOG_WIDTH, 30);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((e) -> {
            shouldDownload = false;
            this.dispose();
        });
        cancelButton.setBounds(10, this.urlField.getY() + 80, DIALOG_WIDTH, 30);
        this.setLayout(null);
        this.add(titleLabel);
        this.add(this.urlField);
        this.add(downloadButton);
        this.add(cancelButton);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(frame);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                VideoUrlDialog.this.shouldDownload = false;
                System.out.println("Window Event");
                super.windowClosed(e);
            }
        });
    }

    public String showDialog() {
        super.setVisible(true);
        System.out.println("Dispose");
        if (this.shouldDownload) {
            String text = this.urlField.getText();
            return text.equals("")? null : text;
        }
        return null;
    }

    @Override
    public void setVisible(boolean b) {
        // Should set visible only by showDialog()
    }
}
