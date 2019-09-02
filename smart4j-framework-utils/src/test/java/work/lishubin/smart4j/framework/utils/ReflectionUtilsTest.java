package work.lishubin.smart4j.framework.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import work.lishubin.smart4j.framework.model.ModelTest;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * @author 李树彬
 * @date 2019/8/30  11:05
 */
public class ReflectionUtilsTest {

    private ModelTest modelTest = null;

    @Before
    public void setUp(){
        modelTest = ReflectionUtils.getNewInstance(ModelTest.class);
    }

    @Test
    public void getNewInstance() {
        Assert.assertNotNull(modelTest);
    }

    @Test
    public void invokeMethod() {
        Assert.assertEquals("hello !test",modelTest.sayHi("test"));
    }

    @Test
    public void setField() throws NoSuchFieldException {


        Field nameField = ModelTest.class.getDeclaredField("name");
        ReflectionUtils.setField(modelTest,nameField,"success");
        Assert.assertEquals("success",modelTest.getName());


    }
}