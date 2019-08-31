package work.lishubin.smart4j.framework.webmvc.core;

import work.lishubin.smart4j.framework.bean.helper.BeanHelper;
import work.lishubin.smart4j.framework.bean.helper.ClassHelper;
import work.lishubin.smart4j.framework.utils.ClassUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author 李树彬
 * @date 2019/8/31  22:30
 */
@WebServlet("/")
public class ServletDispatcher extends HttpServlet {

    private static Set<Class<?>> CONTROLLER_SET;
    static {
        CONTROLLER_SET = ClassHelper.getControllerClass();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
    }


    /**
     * 统一处理请求并转发
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }


}
