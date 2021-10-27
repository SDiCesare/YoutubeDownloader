package com.ike.youtubedownloader;

import com.ike.youtubedownloader.stream.Downloader;

import java.io.File;

/**
 * @author Ike
 * @version 1.0A
 **/
@SuppressWarnings("ALL")
public class Test {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.setDllPath("D:\\Musica\\DLL");
        downloader.setOutPath("D:\\Musica\\Download");

        File dir = new File("D:\\Musica\\Download");
        if (dir.isDirectory() && dir.listFiles() != null) {
            for (File f : dir.listFiles()) {
                String name = f.getName();
                System.out.println("{code=" + name.substring(0, name.lastIndexOf(".")) + "}");
            }
        }

    }

}
