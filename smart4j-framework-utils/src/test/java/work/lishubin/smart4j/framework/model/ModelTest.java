package work.lishubin.smart4j.framework.model;

/**
 * @author 李树彬
 * @date 2019/8/30  11:06
 */
public class ModelTest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String sayHi(String message){
        return String.format("%s!%s", "hello ", message);
    }
}
