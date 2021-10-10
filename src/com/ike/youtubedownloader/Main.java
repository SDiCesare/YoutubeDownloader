package com.ike.youtubedownloader;

import com.ike.youtubedownloader.stream.Downloader;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Main {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.setDllPath("D:\\Musica\\DLL");
        downloader.setOutPath("D:\\Musica\\Download");

    }

}
