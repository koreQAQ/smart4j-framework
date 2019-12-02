package work.lishubin.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * - PropUtils
 * <p>
 * 1. This tool class mainly realizes reading the corresponding Properties file
 * 2. Get the corresponding value according to the given Properties and key
 * 3. Get the corresponding value according to the given Properties and key, defaultValue
 * get value by key, return defaultValue if value does not exist, and set
 *
 * @author lishubin
 * @date 2019/8/29  23:15
 */
public class PropUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtils.class);

    /**
     * Retrieve the information from the corresponding resource file directory
     * and return a Properties object
     * @param fileName filename
     * @return properties
     */
    public static Properties getProperties(String fileName){
        Properties properties = new Properties();
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream!=null){
                //日志信息打印
                LOGGER.info("Loading the configuration file - {}", fileName);
                properties.load(inputStream);
            }
        } catch (IOException e) {
            LOGGER.error("file not found ,Pls check resources",e);
        }
        return properties;

    }

    /**
     * Value by Key from properties, if not, an empty string is returned
     *
     * @param properties from properties
     * @param key        select
     * @return result
     */
    public static String getStringValue(Properties properties,String key){
        return getStringValueWithDefault(properties, key, "");
    }

    /**
     * Get the corresponding value according to the given Properties and key, defaultValue
     * @param properties properties
     * @param key select
     * @param defaultValue value
     * @return result
     */
    public static String getStringValueWithDefault(Properties properties,String key,String defaultValue){
        String value= defaultValue;
        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        } else {
            properties.setProperty(key, defaultValue);
        }
        return value;

    }



}
