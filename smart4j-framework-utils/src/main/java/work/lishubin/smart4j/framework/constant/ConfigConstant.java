package work.lishubin.smart4j.framework.constant;

/**
 * @author 李树彬
 * @date 2019/8/30  0:00
 */
public interface ConfigConstant {

    /**
     * CONFIG FILE
     */
    String CONFIG_FILE = "smart4j.properties";

    String SMART_FRAMEWORK_JDBC_DRIVER="smart.framework.jdbc.driver";
    String SMART_FRAMEWORK_JDBC_URL="smart.framework.jdbc.url";
    String SMART_FRAMEWORK_JDBC_USERNAME="smart.framework.jdbc.username";
    String SMART_FRAMEWORK_JDBC_PASSWORD="smart.framework.jdbc.password";


    String SMART_FRAMEWORK_BASE_PACKAGE="smart.framework.base.package";

    String SMART_FRAMEWORK_APP_JSP_PATH = "smart.framework.app.jsp.path";
    String SMART_FRAMEWORK_APP_ASSETS_PATH = "smart.framework.app.assets.path";

    /**
     * jspPath 与 静态资源文件目录有默认值
     */
    String SMART_FRAMEWORK_APP_JSP_PATH_DEFAULT_VALUE = "/WEB-INF/view/";
    String SMART_FRAMEWORK_APP_ASSETS_PATH_DEFAULT_VALUE = "/WEB-INF/assets/";


}
