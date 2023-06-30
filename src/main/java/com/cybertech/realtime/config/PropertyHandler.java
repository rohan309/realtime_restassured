package com.cybertech.realtime.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {
    Properties properties;
    FileInputStream file;

    public PropertyHandler(String fileName) throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources/";
        file = new FileInputStream(path + fileName);
        properties = new Properties();
        properties.load(file);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void tearDown() throws IOException {
        file.close();
    }
}
