package com.ike.youtubedownloader.stream.callback;

import com.ike.youtubedownloader.swing.Frame;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing Callback Component
 *
 * @author Ike
 * @version 1.0A
 **/
public class JCallback extends JPanel implements DownloadCallback {

    private static final Color DONE = new Color(40, 255, 100);

    private YoutubeVideo video;
    private JLabel process;
    private boolean isDone;
    private float percent;

    /**
     * Constructor
     * Create a JCallback for a {@link YoutubeVideo}
     *
     * @param video The Video to attach to a JCallback
     */
    public JCallback(YoutubeVideo video) {
        super();
        this.video = video;
        this.percent = 0;
        this.process = new JLabel(video.getAuthor() + "-" + video.getTitle());
        this.setLayout(null);
        //this.add(this.process);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.process.setBounds(x, y, width, height);
    }

    /**
     * Start downloading the Video attached to this Callback
     */
    public void start() {
        new Thread(() -> Frame.downloader.download(this.video, this)).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = (int) ((this.getWidth() / 100.f) * this.percent);
        g.setColor(DONE);
        g.fillRect(1, 1, width - 1, this.getHeight() - 2);
        g.setColor(Color.BLACK);
        g.drawString(getSongName() + " [" + percent + "%]", 0, 20);
    }

    /**
     * @return The Song Name in format "author - title"
     */
    private String getSongName() {
        String s = this.video.getAuthor() + "-" + this.video.getTitle();
        return (s.length() > 45) ? s.substring(0, 38) + ".." : s;
    }

    /**
     * Paint a rectangle of green by the percentage of the download
     */
    @Override
    public void callback(String percent, String speed, String eta) {
        if (this.isDone)
            return;
        if (percent.contains("100%")) {
            this.setBackground(DONE);
            this.repaint();
            this.isDone = true;
        }
        this.process.setText(this.video.getAuthor() + "-" + this.video.getTitle() + "[" + percent + "]");
        this.percent = Float.parseFloat(percent.substring(1, percent.indexOf("%")));
        this.repaint();
    }
}
