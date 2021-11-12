package com.ike.youtubedownloader.stream.callback;

/**
 * Interface used to check the download progress for music a/o videos
 *
 * @author Ike
 * @version 1.0A
 **/
public interface DownloadCallback {

    /**
     * Called from a Downloader object during a download of music or video
     *
     * @param percent The percentage downloaded of the song/video (xxx%)
     * @param eta     The time left for the download to finish (hh:mm:ss)
     * @param speed   The download speed (xx b/s)
     */
    void callback(String percent, String speed, String eta);

}
