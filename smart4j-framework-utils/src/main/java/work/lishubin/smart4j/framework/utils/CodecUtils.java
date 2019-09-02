package work.lishubin.smart4j.framework.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * @author lishubin
 */
public class CodecUtils {

    public static String encode(String source){

        String target = source;

        try {
            target = URLEncoder.encode(source, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static String decode(String source){
        String target = source;
        try {
            target = URLDecoder.decode(source, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }



}
