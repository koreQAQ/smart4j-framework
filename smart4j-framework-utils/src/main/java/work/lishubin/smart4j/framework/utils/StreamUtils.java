package work.lishubin.smart4j.framework.utils;

import java.io.*;

/**
 * @author lishubin
 */
public class StreamUtils {


    public static String getString(InputStream inputStream){

        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try{

            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }

}
