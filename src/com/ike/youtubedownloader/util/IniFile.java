package com.ike.youtubedownloader.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Ike
 * @version 1.0A
 **/
public class IniFile {

    private File file;
    private ArrayList<String> names;
    private ArrayList<IniProperty> properties;

    public IniFile(File file) {
        if (!file.getName().endsWith(".ini")) {
            throw new IllegalArgumentException("The file extension must be .ini");
        }
        this.file = file;
        if (this.file.exists()) {
            initProperty();
        } else {
            this.properties = new ArrayList<>();
            this.names = new ArrayList<>();
        }
    }

    public IniFile(String file) {
        this(new File(file));
    }

    public IniFile(String path, String name) {
        this(new File(path, name));
    }

    public void addProperty(String name, IniProperty property) {
        if (this.names.contains(name)) {
            throw new IllegalArgumentException("The Property with name " + name + " already exists");
        }
        this.names.add(name);
        this.properties.add(property);
    }

    public void setName(int index, String name) {
        this.names.set(index, name);
    }

    public void setNameOfProperty(IniProperty property, String name) {
        this.names.set(this.indexOf(property), name);
    }

    public void setProperty(int index, IniProperty property) {
        this.properties.set(index, property);
    }

    public void setPropertyNamed(String name, IniProperty property) {
        this.properties.set(this.indexOf(name), property);
    }

    public String getNameOfProperty(IniProperty property) {
        return this.names.get(this.indexOf(property));
    }

    public String getName(int index) {
        return this.names.get(index);
    }

    public IniProperty getPropertyFromName(String name) {
        return this.properties.get(this.indexOf(name));
    }

    public IniProperty getProperty(int index) {
        if (this.properties.size() <= index)
            return null;
        return this.properties.get(index);
    }

    public int indexOf(IniProperty property) {
        return this.properties.indexOf(property);
    }

    public int indexOf(String name) {
        return this.names.indexOf(name);
    }

    public void save() throws IOException {
        System.out.println("Saving Ini File " + this.file);
        if (file.exists()) {
            System.out.println("Deleting existing one");
            this.file.delete();
        }
        FileWriter fw = new FileWriter(this.file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < this.names.size(); i++) {
            String name = this.names.get(i);
            IniProperty property = this.properties.get(i);
            bw.write("[" + name + "]");
            bw.newLine();
            int lgt = property.getNumberOfProperties();
            for (int j = 0; j < lgt; j++) {
                String[] p = property.getProperty(j);
                bw.write(p[0] + "=" + p[1]);
                bw.newLine();
            }
            bw.write("#=========================");
            bw.newLine();
        }
        // this.file.createNewFile();
        bw.close();
        fw.close();
        System.out.println("Ini file saved.");
    }

    public ArrayList<IniProperty> getProperties() {
        return properties;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setProperties(ArrayList<IniProperty> properties) {
        this.properties = properties;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        this.initProperty();
    }

    public int getNumberOfProperties() {
        return this.names.size();
    }

    private void initProperty() {
        this.properties = new ArrayList<>();
        this.names = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(this.file);
            boolean foundProperty = false;
            IniProperty property = null;
            while (sc.hasNext()) {
                String ln = sc.nextLine();
                if (ln.startsWith("#")) {
                    continue;
                }
                if (ln.startsWith("[")) {
                    if (foundProperty) {
                        this.properties.add(property);
                    } else {
                        foundProperty = true;
                    }
                    property = new IniProperty();
                    this.names.add(ln.substring(1, ln.length() - 1));
                } else if (foundProperty) {
                    if (!ln.contains("=")) continue;
                    String[] values = ln.split("=");
                    String name = values[0];
                    String value = values.length > 1 ? values[1] : "";
                    property.addProperty(name, value);
                }
            }
            if (property != null) {
                this.properties.add(property);
            }
        } catch (IOException e) {
            System.err.println("The specified file doesn't exist");
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(super.toString());
        out.append("[").append(this.file.toString()).append(",");
        for (int i = 0; i < this.getNumberOfProperties(); i++) {
            String name = this.names.get(i);
            IniProperty property = this.properties.get(i);
            out.append(name).append("=").append(property.toString());
            if (i == this.getNumberOfProperties() - 1) {
                return out.append("]").toString();
            } else {
                out.append(",");
            }
        }
        return out.toString();
    }

    public static class IniProperty {

        private final ArrayList<String> names;
        private final ArrayList<String> values;

        public IniProperty() {
            this.names = new ArrayList<>();
            this.values = new ArrayList<>();
        }

        public IniProperty(ArrayList<String> names, ArrayList<String> values) {
            this.names = names;
            this.values = values;
        }

        public IniProperty(String[] names, String[] values) {
            this.names = new ArrayList<>(Arrays.asList(names));
            this.values = new ArrayList<>(Arrays.asList(values));
        }

        public int indexOfName(String name) {
            return this.names.indexOf(name);
        }

        public int indexOfValue(String value) {
            return this.values.indexOf(value);
        }

        public int getNumberOfProperties() {
            return this.names.size();
        }

        public void addProperty(String name, String value) {
            this.names.add(name);
            this.values.add(value);
        }

        public String[] getProperty(int index) {
            return new String[]{getName(index), getValue(index)};
        }

        public void setProperty(int index, String name, String value) {
            this.names.set(index, name);
            this.values.set(index, value);
        }

        public ArrayList<String> getNames() {
            return names;
        }

        public String getName(String value) {
            return this.names.get(this.indexOfValue(value));
        }

        public String getName(int index) {
            return this.names.get(index);
        }

        public void setName(int index, String name) {
            this.names.set(index, name);
        }

        public void setNameFromValue(String value, String name) {
            this.names.set(this.indexOfValue(value), name);
        }

        public String getValue(String name) {
            int index = this.indexOfName(name);
            return (index == -1) ? null : this.values.get(index);
        }

        public String getValue(int index) {
            return this.values.get(index);
        }

        public void setValue(int index, String value) {
            this.values.set(index, value);
        }

        public void setValueNamed(String name, String value) {
            this.values.set(this.indexOfName(name), value);
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder(super.toString());
            out.append("[");
            for (int i = 0; i < this.getNumberOfProperties(); i++) {
                String[] property = this.getProperty(i);
                out.append(property[0]).append("=").append(property[1]);
                if (i == this.getNumberOfProperties() - 1) {
                    return out.append("]").toString();
                } else {
                    out.append(",");
                }
            }
            return out.toString();
        }
    }

}
