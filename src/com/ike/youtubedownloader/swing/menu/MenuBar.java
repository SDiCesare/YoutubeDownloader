package com.ike.youtubedownloader.swing.menu;

import javax.swing.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class MenuBar extends JMenuBar {

    public MenuBar() {
        super();
        JMenu settings = new JMenu("Settings");
        JCheckBoxMenuItem directDownload = new JCheckBoxMenuItem("Direct Download");
        directDownload.setState(false);
        settings.add(directDownload);
        this.add(settings);
    }

}
