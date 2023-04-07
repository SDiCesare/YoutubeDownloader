package com.ike.youtubedownloader.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Settings {

    public static final String DIRECT_DOWNLOAD = "direct_download";
    public static final String SAVE_ARTIST = "save_artist";
    public static final String RESULTS = "results";
    public static final String DOWNLOAD_DIR = "download_dir";
    public static final String YOUTUBE_DL_PATH = "youtube_dll_path";
    public static final String FFMPEG_DL_PATH = "ffmpeg_dll_path";

    private static final String[] SETTINGS_NAME;
    private static final String[] DEFAULT_VALUES;
    private static final HashMap<String, String> settings;
    private static IniFile settingsFile;

    static {
        SETTINGS_NAME = new String[]{DIRECT_DOWNLOAD, SAVE_ARTIST, RESULTS, DOWNLOAD_DIR, YOUTUBE_DL_PATH, FFMPEG_DL_PATH};
        DEFAULT_VALUES = new String[]{"false", "true", "30", "", "", "", ""};
        settings = new HashMap<>();
        loadFrom(new File("downloaderSettings.ini"));
    }

    public static void loadFrom(File f) {
        if (f.exists()) {
            System.out.println("Reading Settings from " + f + ".");
        } else {
            System.out.println("Creating new settings file.");
        }
        settingsFile = new IniFile(f);
        IniFile.IniProperty property;
        if (settingsFile.getNumberOfProperties() < 1) {
            property = new IniFile.IniProperty();
            settingsFile.addProperty("settings", property);
        } else {
            property = settingsFile.getProperty(0);
        }
        for (int i = 0; i < SETTINGS_NAME.length; i++) {
            String name = SETTINGS_NAME[i];
            String value = property.getValue(name);
            if (value == null) {
                property.addProperty(name, DEFAULT_VALUES[i]);
                value = DEFAULT_VALUES[i];
            }
            settings.put(name, value);
        }
    }

    public static void change(String key, String value) {
        if (settings.get(key).equals(value))
            return;
        settings.replace(key, value);
    }

    public static String get(String key) {
        return settings.get(key);
    }

    public static void saveTo(File file) {
        try {
            IniFile out = new IniFile(file);
            IniFile.IniProperty property = new IniFile.IniProperty();
            out.addProperty("settings", property);
            for (String settingName : SETTINGS_NAME) {
                String value = settings.get(settingName);
                property.addProperty(settingName, value);
            }
            out.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void save() {
        try {
            IniFile.IniProperty property = settingsFile.getProperty(0);
            for (String settingName : SETTINGS_NAME) {
                String value = settings.get(settingName);
                property.setValueNamed(settingName, value);
            }
            settingsFile.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
