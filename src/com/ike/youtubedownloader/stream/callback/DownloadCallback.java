package com.ike.youtubedownloader.stream.callback;

/**
 * @author Ike
 * @version 1.0A
 **/
public interface DownloadCallback {

    void callback(String percent, String speed, String eta);

}
