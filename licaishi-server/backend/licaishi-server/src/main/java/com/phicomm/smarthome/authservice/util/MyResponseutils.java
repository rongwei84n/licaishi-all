package com.phicomm.smarthome.authservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wenhua.tang
 *
 */
public class MyResponseutils {

    private static final Logger LOGGER = LogManager.getLogger(MyResponseutils.class);
    public static final String ERRORCODE_FILE_PATH = "/errorCode.properties";
    public static Properties prop = loadProperties();

    /**
     * 获取错误码对应的错误信息
     *
     * @param code
     * @return
     */
    public static String parseMsg(int code) {
        String value = prop.getProperty(Integer.toString(code));
        if (!StringUtil.isNullOrEmpty(value)) {
            return value;
        }
        return "请求失败";
    }

    /**
     * 加载错误码配置
     *
     * @return properties
     */
    public static Properties loadProperties() {
        Properties properties = null;
        InputStream fis = null;
        try {
            fis = MyResponseutils.class.getResourceAsStream(ERRORCODE_FILE_PATH);
            properties = new Properties();
            properties.load(new InputStreamReader(fis, "utf-8"));
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        }
        LOGGER.info("load errorCode.properties");
        return properties;
    }
}
