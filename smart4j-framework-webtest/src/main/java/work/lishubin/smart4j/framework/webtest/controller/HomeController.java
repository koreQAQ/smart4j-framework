package work.lishubin.smart4j.framework.webtest.controller;

import work.lishubin.smart4j.framework.webmvc.annotation.Action;
import work.lishubin.smart4j.framework.webmvc.annotation.Controller;
import work.lishubin.smart4j.framework.webmvc.entity.HttpMethod;
import work.lishubin.smart4j.framework.webmvc.entity.View;

/**
 * @author lishubin
 */
@Controller
public class HomeController {

    @Action(path = "/home", httpMethod = HttpMethod.GET)
    public View home(){
        View view = View.getInstance("home.jsp");
        view.addModel("message","message");
        return view;

    }

}
