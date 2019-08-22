package com.neuedu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tao on 2019/8/16.
 */
public class PropertiesUtils {

    private static Properties properties=new Properties();

    static {
        InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("host.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readByKey(String key){
        return properties.getProperty(key);
    }


}
