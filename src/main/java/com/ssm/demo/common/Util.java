package com.ssm.demo.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/4/26
 */
public class Util {
    static Properties properties = null;

    public static String getConfig(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(Util.class.getResourceAsStream("/application.properties"), "UTF-8"));
        String message = properties.getProperty(key);
        return message;
    }

    public static void loadMessage() throws IOException {
        properties = new Properties();
        properties.load(new InputStreamReader(Util.class.getResourceAsStream("/message.properties"), "UTF-8"));
    }

    public static String getMessage(String key, String... argv) {
        if(properties == null) {
            try {
                loadMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String message = properties.getProperty(key);

        if(argv != null) {
            for (String arg: argv) {
                message = message.replaceFirst("\\{[0-9]*\\}", arg);
            }
        }

        return message;
    }

    /**
     * URI参数编码
     * @param param
     * @return
     */
    public static String encodeUriParam(String param) {
        try {
            if(param == null) {
                return null;
            }
            return new String(param.getBytes("UTF-8"), "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
