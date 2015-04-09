package com.rrs.session;

import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * Created by zhaop01 on 2015/4/8.
 */
public class CustomerHttpServletRequest extends HttpServletRequestWrapper {


    @Setter
    private CustomerSession customerSession;

    public CustomerHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public HttpSession getSession() {
        return this.customerSession;
    }
}
