package work.lishubin.smart4j.framework.webmvc.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * @author 李树彬
 * @date 2019/8/31  22:29
 */
public class View {

    private String viewPath;

    /**
     * 向视图层传递的参数
     */
    private Map<String,Object> model;

    private View(String viewPath){
        this.viewPath = viewPath;
        this.model = new HashMap<>();
    }

    public static View getInstance(String viewPath){
        return new View(viewPath);
    }

    public void addModel(String name,Object value){
        this.model.put(name, value);
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public Map<String, Object> getModel() {
        return model;
    }

}
