package work.lishubin.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ProUtils Used to read configuration file information
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
                properties.load(inputStream);
            }
        } catch (IOException e) {
            LOGGER.error("file not found ,Pls check resources",e);
        }
        return properties;

    }

    /**
     * Value by Key from properties, if not, an empty string is returned
     * @param properties to
     * @param key select
     * @return result
     */
    public static String getStringValue(Properties properties,String key){
        return getStringValueWithDefault(properties, key, "");
    }

    /**
     * Provides methods to return default values
     * @param properties properties
     * @param key select
     * @param defaultValue value
     * @return result
     */
    public static String getStringValueWithDefault(Properties properties,String key,String defaultValue){
        String value= defaultValue;
        if (properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;

    }



}
