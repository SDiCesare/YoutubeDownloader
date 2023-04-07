package com.ike.youtubedownloader.video;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class VideoFile {

    public static void saveOnFile(File file, ArrayList<YoutubeVideo> videos) {
        saveOnFile(file, videos.toArray(new YoutubeVideo[0]));
    }

    public static void saveOnFile(File file, YoutubeVideo... videos) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileWriter w = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(w);
            for (int i = 0; i < videos.length; i++) {
                YoutubeVideo video = videos[i];
                writer.write(video.toJson());
                if (i == videos.length - 1)
                    break;
                writer.write('\n');
            }
            writer.close();
            w.close();
            System.out.println("File Saved Successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<YoutubeVideo> loadFromFile(File file) {
        ArrayList<YoutubeVideo> videos = new ArrayList<>();
        try {
            if (!file.exists()) {
                throw new FileNotFoundException(file.toString());
            }
            if (!file.getName().endsWith(".ytv")) {
                throw new IllegalArgumentException("Invalid YoutubeVideo File extension " + file.getName());
            }
            FileReader r = new FileReader(file);
            BufferedReader reader = new BufferedReader(r);
            String ln = reader.readLine();
            while (ln != null) {
                videos.add(YoutubeVideo.fromJson(ln));
                ln = reader.readLine();
            }
            reader.close();
            r.close();
            System.out.println("Video Load Successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return videos;
    }

}
