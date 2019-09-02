package work.lishubin.smart4j.framework.webtest.controller;

import work.lishubin.smart4j.framework.bean.annotation.Action;
import work.lishubin.smart4j.framework.bean.annotation.Controller;
import work.lishubin.smart4j.framework.webmvc.entity.Param;
import work.lishubin.smart4j.framework.webmvc.entity.View;

/**
 * @author lishubin
 */
@Controller
public class HomeController {

    @Action(value = "get:/home")
    public View home(){
        View view = View.getInstance("home.jsp");
        view.addModel("message","message");
        return view;

    }

}
