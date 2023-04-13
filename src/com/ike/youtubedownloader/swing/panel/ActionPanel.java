package com.ike.youtubedownloader.swing.panel;

import com.ike.youtubedownloader.stream.callback.DownloadAction;
import com.ike.youtubedownloader.swing.dialog.VideoUrlDialog;
import com.ike.youtubedownloader.util.ResourceLoader;
import com.ike.youtubedownloader.util.YoutubeVideoUtil;
import com.ike.youtubedownloader.video.YoutubeVideo;

import javax.swing.*;

public class ActionPanel extends JPanel {

    private JButton downloadButton;
    private JButton settingsButton;
    private DownloadAction downloadAction;

    public ActionPanel(JFrame frame) {
        super();
        this.downloadButton = new JButton(ResourceLoader.DOWNLOAD_ICON);
        this.downloadButton.setBounds(0, 0, 60, 60);
        this.downloadButton.addActionListener((e) -> {
            if (downloadAction == null)
                return;
            String url = new VideoUrlDialog(frame).showDialog();
            if (url == null)
                return;
            if (YoutubeVideoUtil.isPlaylist(url)) {
                YoutubeVideo[] youtubeVideos = YoutubeVideoUtil.retrieveVideosFromPlaylistUrl(url);
                if (youtubeVideos == null)
                    return;
                for (YoutubeVideo video : youtubeVideos) {
                    downloadAction.perform(video);
                }
            } else {
                YoutubeVideo video = YoutubeVideoUtil.retrieveVideoFromUrl(url);
                downloadAction.perform(video);
            }
        });
        this.settingsButton = new JButton(ResourceLoader.SETTINGS_ICON);
        this.settingsButton.setBounds(60, 0, 60, 60);
        this.settingsButton.addActionListener((e) -> {
            System.out.println("Settings");
        });
        this.setLayout(null);
        this.add(this.downloadButton);
        this.add(this.settingsButton);
    }

    public void setDownloadAction(DownloadAction downloadAction) {
        this.downloadAction = downloadAction;
    }
}
