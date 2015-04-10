package com.rrs.seller.apps.web;

import com.rrs.seller.model.Seller;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaop01 on 2015/4/8.
 */
@Controller
@RequestMapping(value = "/api")
public class Application {

    @RequestMapping(value = "/say.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Seller say(HttpServletRequest request){
        Seller seller = new Seller();
        seller.setId(10);
        seller.setName("国语");
        seller.setEmail("wq@153.com");
        return seller;
    }

    @RequestMapping(value = "/say2.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Seller say2(HttpServletRequest request,HttpServletResponse response){
        Seller seller = new Seller();
        seller.setId(10);
        seller.setName("国语");
        seller.setEmail("wq@153.com");

        Seller seller1 = new Seller();
        seller1.setId(20);
        seller1.setName("国2语");
        seller1.setEmail("wq@163.com");
        seller.getLists().add(seller1);
        return seller;
    }

}
