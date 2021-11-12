package com.ike.youtubedownloader.stream;

import com.ike.youtubedownloader.stream.callback.DownloadCallback;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A class used for filter the print output from cmd
 *
 * @author Ike
 * @version 1.0A
 **/
public class SyncPipe implements Runnable {

    private final OutputStream outputStream;
    private final InputStream inputStream;
    private DownloadCallback callback;

    public SyncPipe(InputStream inputStream, OutputStream outputStream, DownloadCallback callback) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.callback = callback;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length; (length = inputStream.read(buffer)) != -1; ) {
                //outputStream.write(buffer, 0, length);
                String text = new String(buffer);
                if (text.contains("[download]")) {
                    this.callback(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //[download]  33.9% of 3.52MiB at 21.00KiB/s ETA 01:53ad\song.mp3" https://www.youtube.com/watch?v=lhbA8f5Bf8E

    /**
     * Calls the callback for this object
     */
    private void callback(String text) {
        int i = text.indexOf("%");
        if (i == -1)
            return;
        String percent = text.substring(text.indexOf("]") + 1, i + 1);
        i = text.indexOf("at");
        int etaIndex = text.indexOf("ETA");
        if (i == -1 || etaIndex == -1 || (i + 3) > etaIndex)
            return;
        String speed = text.substring(i + 3, etaIndex);
        String eta = text.substring(etaIndex + 4, etaIndex + 9);
        this.callback.callback(percent, speed, eta);
    }

}