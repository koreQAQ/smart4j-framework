package work.lishubin.smart4j.framework.constant;

public interface MvcConfigStant extends ConfigConstant {

    /**
     * 视图文件扩展名
     */
    String VIEW_APPENDIX = ".jsp";

    /**
     * 视图存放位置
     */
    String SMART_FRAMEWORK_APP_JSP_PATH = "smart.framework.app.jsp.path";
    String SMART_FRAMEWORK_APP_ASSETS_PATH = "smart.framework.app.assets.path";

    /**
     * jspPath 与 静态资源文件目录有默认值
     */
    String SMART_FRAMEWORK_APP_JSP_PATH_DEFAULT_VALUE = "/WEB-INF/view/";
    String SMART_FRAMEWORK_APP_ASSETS_PATH_DEFAULT_VALUE = "/WEB-INF/assets/";


}
