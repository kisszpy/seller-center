package com.rrs.session;


import com.rrs.constants.Constants;
import com.rrs.utils.ApplicationContextHolder;
import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhaop01 on 2015/4/8.
 */
@Log
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setCharacterEncoding(Constants.DEFAULT_CHAR_SET);
        CustomerHttpServletRequest customerHttpServletRequest = new CustomerHttpServletRequest(request);
        SessionManager sessionManager = ApplicationContextHolder.getBean(SessionManager.class);
        CustomerSession session = (CustomerSession) sessionManager.createSession(customerHttpServletRequest,response);
        customerHttpServletRequest.setCustomerSession(session);
        filterChain.doFilter(customerHttpServletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
