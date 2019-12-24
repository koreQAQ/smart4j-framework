package work.lishubin.smart4j.framework.webmvc.core;

import work.lishubin.smart4j.framework.bean.helper.BeanHelper;
import work.lishubin.smart4j.framework.bean.helper.ConfigHelper;
import work.lishubin.smart4j.framework.utils.*;
import work.lishubin.smart4j.framework.webmvc.entity.*;
import work.lishubin.smart4j.framework.webmvc.helper.ControllerHelper;
import work.lishubin.smart4j.framework.webmvc.helper.MvcBeanLoaderHelper;
import work.lishubin.smart4j.framework.webmvc.helper.ServletHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 统一的入口转发器
 *
 * @author 李树彬
 * @date 2019/8/31  22:30
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class ServletDispatcher extends HttpServlet {


    @Override
    public void init(ServletConfig config) {


        // 初始化MvcBeanLoaderHelper
        // 完成对应的依赖注入
        MvcBeanLoaderHelper.init();


        // 获得整个Servlet的环境
        ServletContext servletContext = config.getServletContext();

        // 注册用于Jsp处理的Servlet
        ServletRegistration jspServletRegistration = servletContext.getServletRegistration("jsp");
        // 建立起jsp的映射关系
        jspServletRegistration.addMapping(
                String.format(
                        "%s%s",
                        ConfigHelper.getAppJspPath(),"*"));


        // 设置默认的资源文件映射
        ServletRegistration defaultServletRegistration = servletContext.getServletRegistration("default");
        defaultServletRegistration.addMapping(
                String.format(
                        "%s%s",
                        ConfigHelper.getAppAssetsPath(),"*"
                        ));
    }


    /**
     * 统一处理请求并转发
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletHelper.init(request, response);

        try {


            // 获取请求的信息
            String urlMethod = request.getMethod().toLowerCase();
            HttpMethod requestMethod = getHttpMethod(urlMethod);


            String requestUrl = request.getPathInfo();

            //得到对应的处理器对象
            SmartHandler smartHandler = ControllerHelper.getSmartHandler(requestMethod, requestUrl);

            // 查到存在对应的Handler
            if (!Objects.isNull(smartHandler)) {

                // 得到对应的Controller
                Class<?> controllerClass = smartHandler.getController();
                // 得到对应的Controller实体
                Object controllerBean = BeanHelper.getBean(controllerClass);
                // 得到映射的方法
                Method controllerMethod = smartHandler.getMethod();


                // 封装请求参数列表，向方法进行传递
                Param param = Param.getNewInstance(new HashMap<>(20));

                // 遍历整个request的请求参数，将其放入到Param中
                Enumeration<String> attributeNames = request.getAttributeNames();

                while (attributeNames.hasMoreElements()) {
                    String attributeName = attributeNames.nextElement();
                    Object attributeValue = request.getAttribute(attributeName);

                    param.addAttribute(attributeName, attributeValue);
                }

                // 遍历请求url中的参数封装
                String body = CodecUtils.decode(StreamUtils.getString(request.getInputStream()));

                if (StringUtils.isNotEmpty(body)) {

                    String[] paramArray = StringUtils.spiltString(body, "&");

                    // 遍历每一组参数
                    for (String eachParam : paramArray) {

                        String[] paramWithKeyAndValue = StringUtils.spiltString(eachParam, "=");

                        if (paramWithKeyAndValue.length == 2) {

                            String paramKey = paramWithKeyAndValue[0];
                            String paramValue = paramWithKeyAndValue[1];
                            param.addAttribute(paramKey, paramValue);
                        }
                    }
                }


                // 调用对应的方法得到方法的返回结果
                Object methodResult = ReflectionUtils.invokeMethod(controllerBean, controllerMethod, param);


                // 如果返回结果是视图
                if (methodResult instanceof View) {
                    View view = (View) methodResult;
                    String viewPath = view.getViewPath();

                    // viewPath 有两种
                    // - 转发 home
                    // - 重定向 redirect: home
                    String[] viewPathWithMethod = viewPath.split(":");
                    // 转发情况
                    // 长度为1 则是转发请求
                    if (viewPathWithMethod.length == 1) {

                        // 清除左右的 空格
                        viewPath = viewPath.trim();
                        // 页面Model传递
                        // 放入model进入request
                        Map<String, Object> viewModel = view.getModel();
                        for (String key : viewModel.keySet()) {
                            request.setAttribute(key, viewModel.get(key));
                        }
                        request.getRequestDispatcher(
                                String.format("%s%s",
                                        ConfigHelper.getAppJspPath(), viewPath))
                                .forward(request, response);
                    }
                    // 长度为2 重定向
                    else {
                        // 重定向的相关设置
                        String redirectPath = viewPathWithMethod[1];
                        redirectPath = redirectPath.trim();
                        response.sendRedirect(String.format("%s%s", request.getContextPath(), redirectPath));
                    }
                }
                // 如果返回结果是数据
                else if (methodResult instanceof Data) {
                    Data data = (Data) methodResult;
                    // 获取封装的数据模型
                    Map<String, Object> model = data.getModel();
                    String parseString = JsonUtils.parseString(model);

                    // 设置response的输出
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(parseString);
                    writer.flush();
                    writer.close();
                }

            }

            // 返回404页面
            else {
                //设置返回页面的方式
                response.setContentType("text/html");
                //设置编码
                response.setCharacterEncoding("UTF-8");


                PrintWriter writer = response.getWriter();
                String contextHtml = CodecUtils.decode(StreamUtils.getString(
                        Thread.currentThread().getContextClassLoader()
                                .getResourceAsStream("error.html")));
                writer.write(contextHtml);
                writer.flush();
                writer.close();

            }
        } finally {
            ServletHelper.destroy();
        }
    }

    /**
     * 将对应request得到的method字符串转化为对应的枚举对象
     *
     * @param urlMethod
     * @return
     */
    private HttpMethod getHttpMethod(String urlMethod) {

        HttpMethod method = null;
        if (urlMethod.toLowerCase().equals("get")) {
            method = HttpMethod.GET;
        }
        if (urlMethod.toLowerCase().equals("post")) {
            method = HttpMethod.POST;
        }

        return method;


    }

}
