package work.lishubin.smart4j.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lishubin
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static String parseString(Object source){

        String writeValueAsString = "";
        try {
            writeValueAsString = OBJECT_MAPPER.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            LOGGER.error("parse Json failure" ,e );
        }
        return writeValueAsString;
    }


}
