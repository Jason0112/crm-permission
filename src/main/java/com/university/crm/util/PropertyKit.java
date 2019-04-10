package com.university.crm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * date: 2019/3/15
 * description :
 *
 * @author : zhencai.cheng
 */
public class PropertyKit {

    private static final Logger logger = LoggerFactory.getLogger(PropertyKit.class);

    /**
     * 获取配置文件
     *
     * @param propertyFileName 配置文件名  resources/test.properties propertyFileName 传test.properties
     * @return 配置属性
     */
    public static Properties getProperty(String propertyFileName) {
        Properties properties = new Properties();
        InputStream in = PropertyKit.class.getClassLoader().getResourceAsStream(propertyFileName);
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        try {
            properties.load(bf);
        } catch (IOException e) {
            logger.error("{}-加载读取失败", propertyFileName);
        }
        return properties;
    }


    /**
     * 获取配置文件
     *
     * @param propertyFileName 配置文件名
     * @param propertyKey      属性key
     * @return 配置属性
     */
    public static String getProperty(String propertyFileName, String propertyKey) {
        Properties properties = getProperty(propertyFileName);
        return properties.getProperty(propertyKey);
    }

    /**
     * 获取配置文件
     *
     * @param propertyFileName 配置文件名
     * @param propertyKey      属性key
     * @param defaultValue     属性默认值
     * @return 配置属性
     */
    public static String getProperty(String propertyFileName, String propertyKey, String defaultValue) {
        Properties properties = getProperty(propertyFileName);
        return properties.getProperty(propertyKey, defaultValue);
    }

    /**
     * @param baseName resources/test.properties propertyFileName 传test
     * @return 配置属性
     */
    public static ResourceBundle getBundle(String baseName) {
        return ResourceBundle.getBundle(baseName);
    }

    public static String getBundleValue(String baseName, String key) {
        ResourceBundle bundle = getBundle(baseName);
        return bundle.getString(key);
    }

    public static String getBundleValue(String baseName, String key, String defaultValue) {
        ResourceBundle bundle = getBundle(baseName);
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return defaultValue;
        }

    }


    public static void main(String[] args) {
        String value = PropertyKit.getBundleValue("lang", "test");
        System.out.println(value);
        value = PropertyKit.getProperty("lang.properties", "test");
        System.out.println(value);
    }
}
