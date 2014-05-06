package com.ctrip.gs.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * config file load and reading class
 * 
 * @author wgji
 * 
 */
public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static final Properties properties = new Properties();

    static {
        initializeDir(Thread.currentThread().getContextClassLoader().getResource("").getPath());
    }

    public static void initialize(String fileName) {
        properties.putAll(getProperties(fileName));
    }

    public static void initializeDir(String directory) {
        File pathFile = new File(directory);
        File[] files = pathFile.listFiles(new ProductFileFilter());
        for (File file : files) {
            initialize(file.getName());
        }
        files = pathFile.listFiles(new UatFileFilter());
        for (File file : files) {
            initialize(file.getName());
        }
        files = pathFile.listFiles(new FatFileFilter());
        for (File file : files) {
            initialize(file.getName());
        }
        files = pathFile.listFiles(new DevFileFilter());
        for (File file : files) {
            initialize(file.getName());
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static Integer getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public static Boolean getBool(String key) {
        return getString(key).toLowerCase().trim().equals("true");
    }

    public static Properties getProperties(String filename) {
        Properties prop = new Properties();
        // InputStream ins = null;
        Reader reader = null;
        try {
            // ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            reader = new InputStreamReader(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(filename), "UTF-8");

            logger.info("load properties with filename: " + filename);
            if (reader != null) {
                prop.load(reader);
            }
        } catch (Exception ex) {
            logger.error("Error while loading file: " + filename, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                logger.error("Error while close inputstream: " + filename, ex);
            }
        }
        return prop;
    }

    static class ProductFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.contains(".properties") && !name.contains("log4j.properties")
                    && !name.contains("-uat.properties") && !name.contains("-fat.properties")
                    && !name.contains("-local.properties");
        }
    }

    static class UatFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.contains("-uat.properties");
        }
    }

    static class FatFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.contains("-fat.properties");
        }
    }

    static class DevFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.contains("-local.properties");
        }
    }
}
