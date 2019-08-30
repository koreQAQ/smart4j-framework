package work.lishubin.smart4j.framework.utils;

/**
 * @author 李树彬
 * @date 2019/8/30  9:58
 */
public class StringUtils {

    public static Boolean isNotEmpty(String checkString){

        return org.apache.commons.lang3.StringUtils.isNotEmpty(checkString);

    }

    public static Boolean isEmpty(String checkString){
        return !isNotEmpty(checkString);
    }

}
