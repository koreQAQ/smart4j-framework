package work.lishubin.smart4j.framework.webmvc.helper;

import work.lishubin.smart4j.framework.commons.MyThreadLocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lenovo
 */
public class ServletHelper {

    /**
     * 线程安全的ServletHelper
     */
    private static final MyThreadLocal<ServletHelper> CONTAINER = new MyThreadLocal<>();

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    private ServletHelper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    /**
     * 初始化ServletHelper ， 放入对应的HTTPServletRequest 和 HttpServletResponse
     *
     * @param httpServletRequest  从ServletDispatcher获取到的request
     * @param httpServletResponse ServletDispatcher获取到的response
     */
    public static void init(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        ServletHelper servletHelper = new ServletHelper(httpServletRequest, httpServletResponse);
        CONTAINER.setValue(servletHelper);

    }


    /**
     * 将线程中的ServletHelper删除
     */
    public static void destroy() {
        CONTAINER.remove();
    }

    private static HttpServletRequest getRequest() {
        ServletHelper servletHelper = CONTAINER.get();
        return servletHelper.httpServletRequest;
    }

    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    public static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }


    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key) {

        return (T) getRequest().getAttribute(key);

    }

    private static HttpSession getSession() {
        ServletHelper servletHelper = CONTAINER.get();
        return servletHelper.httpServletRequest.getSession();
    }

    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeSessionAttribute(String key) {
        getSession().removeAttribute(key);
    }


    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String key) {

        return (T) getSession().getAttribute(key);

    }


}
