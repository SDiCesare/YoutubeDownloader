package com.ike.youtubedownloader.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {

    public static final ImageIcon DOWNLOAD_ICON;
    public static final ImageIcon SETTINGS_ICON;

    static {
        DOWNLOAD_ICON = loadIcon("download.png");
        SETTINGS_ICON = loadIcon("settings.png");
    }

    private static ImageIcon loadIcon(String name) {
        try {
            return new ImageIcon(ImageIO.read(getResources("icon", name)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static InputStream getResources(String path, String name) throws IOException {
        String name1 = "res/" + path + "/" + name;
        System.out.println(name1);
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        return classLoader.getResourceAsStream(name1);
    }

}
