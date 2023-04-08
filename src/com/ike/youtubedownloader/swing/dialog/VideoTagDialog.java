package com.ike.youtubedownloader.swing.dialog;

import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VideoTagDialog extends JDialog {

    private static final int DIALOG_WIDTH = 160;

    private YoutubeVideo video;
    private JTextField titleField;
    private JTextField authorField;
    private boolean shouldSave;

    public VideoTagDialog(JFrame frame, YoutubeVideo video) {
        super(frame);
        this.setTitle("Tag for: " + video.getCode());
        this.setSize(180, 350);
        this.video = video;
        this.shouldSave = false;
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(10, 110, DIALOG_WIDTH, 30);
        this.titleField = new JTextField(video.getTitle());
        this.titleField.setBounds(10, 140, DIALOG_WIDTH, 30);
        JLabel authorLabel = new JLabel("Artist");
        authorLabel.setBounds(10, 170, DIALOG_WIDTH, 30);
        this.authorField = new JTextField(video.getAuthor());
        this.authorField.setBounds(authorLabel.getX(), authorLabel.getY() + 30, this.titleField.getWidth(), this.titleField.getHeight());
        JButton saveButton = new JButton("Change Tag");
        saveButton.addActionListener((e) -> {
            shouldSave = true;
            this.dispose();
        });
        saveButton.setBounds(10, this.authorField.getY() + 40, DIALOG_WIDTH, 30);
        JButton cancelButton = new JButton("Skip");
        cancelButton.addActionListener((e) -> {
            shouldSave = false;
            this.dispose();
        });
        cancelButton.setBounds(10, this.authorField.getY() + 80, DIALOG_WIDTH, 30);
        this.setLayout(null);
        this.add(titleLabel);
        this.add(this.titleField);
        this.add(authorLabel);
        this.add(this.authorField);
        this.add(saveButton);
        this.add(cancelButton);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(frame);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                VideoTagDialog.this.shouldSave = false;
                System.out.println("Window Event");
                super.windowClosed(e);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(this.video.getThumbnail(), 10, 10, DIALOG_WIDTH, 90, null);
    }

    public YoutubeVideo showDialog() {
        super.setVisible(true);
        System.out.println("Dispose");
        if (this.shouldSave) {
            this.video.setTitle(this.titleField.getText());
            this.video.setAuthor(this.authorField.getText());
        }
        return this.video;
    }

    @Override
    public void setVisible(boolean b) {
        // Should set visible only by showDialog()
    }
}
