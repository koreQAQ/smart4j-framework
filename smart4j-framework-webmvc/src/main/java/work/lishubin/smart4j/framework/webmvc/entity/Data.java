package work.lishubin.smart4j.framework.webmvc.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李树彬
 * @date 2019/8/31  22:29
 */
public class Data {

    private Map<String, Object> model;

    private Data(Map<String, Object> model){
        this.model = model;
    }

    public static Data getNewInstance(Map<String, Object> model){
        return new Data(model);
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
