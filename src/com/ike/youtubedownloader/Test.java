package com.ike.youtubedownloader;

import com.ike.youtubedownloader.stream.Downloader;
import com.ike.youtubedownloader.stream.Searcher;

import java.io.File;

/**
 * @author Ike
 * @version 1.0A
 **/
@SuppressWarnings("ALL")
public class Test {

    public static void main(String[] args) {
        Searcher.search("https://www.youtube.com/watch?v=42miluNtzEk&list=PLlHv2_0xmZFTP0s6CujOv6jmh9MrkgCmq&index=10", 0);
    }

}
