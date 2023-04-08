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
    public void downloadCallback(int percent, String speed, String eta) {
        System.out.println("[download] " + percent + "%, ETA " + eta);
    }

    @Override
    public void messageCallback(String msg) {
        System.out.println(msg);
    }

    @Override
    public void messageEnd(Throwable t) {
        if (t == null)
            return;
        t.printStackTrace();
    }


}
