package com.ike.youtubedownloader.stream.callback;

/**
 * A simple Callback for downloader
 * Used for tests
 *
 * @author Ike
 * @version 1.0A
 **/
public class SimpleDownloaderCallback implements DownloadCallback {
    @Override
    public void downloadCallback(String percent, String speed, String eta) {
        System.out.printf("\r[download] %s, ETA %s", percent, eta);
    }

    @Override
    public void messageCallback(String msg) {
        System.out.println(msg);
    }
}
