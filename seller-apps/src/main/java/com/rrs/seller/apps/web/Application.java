package com.rrs.seller.apps.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaop01 on 2015/4/8.
 */
@Controller
@RequestMapping(value = "/api")
public class Application {

    @RequestMapping(value = "/say.do")
    @ResponseBody
    public void say(HttpServletRequest request){
        System.out.println("say");
        request.getSession().setAttribute("login_session_key",12312313);
    }

}
