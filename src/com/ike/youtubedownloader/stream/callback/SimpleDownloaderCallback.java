package com.ike.youtubedownloader.stream.callback;

/**
 * @author Ike
 * @version 1.0A
 **/
public class SimpleDownloaderCallback implements DownloadCallback {
    @Override
    public void callback(String percent, String speed, String eta) {
        System.out.printf("\r[download] %s, ETA %s", percent, eta);
    }
}
