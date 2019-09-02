package work.lishubin.smart4j.framework.webmvc.entity;


import java.util.HashMap;
import java.util.Map;

/**
 * 定义参数
 */
public class Param {


    private Map<String, Object> paramMap;

    private Param(Map<String, Object> paramMap){
        this.paramMap = paramMap;
    }

    public static Param getNewInstance(Map<String,Object> paramMap){
        return new Param(paramMap);
    }


    public void addAttribute(String attributeName,Object attributeValue){
        this.paramMap.put(attributeName, attributeValue);
    }


    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
