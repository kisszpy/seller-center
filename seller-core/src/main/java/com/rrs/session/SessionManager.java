package com.rrs.session;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zhaop01 on 2015/4/8.
 */
public interface SessionManager {


    /**
     * 修改过期时间
     * @param key
     */
    public void updateExpire(String key);

    /**
     * 修改session数据
     * @param session
     */
    public void updateSession(CustomerSession session);
    /**
     * 创建session
     */
    public HttpSession createSession(CustomerHttpServletRequest request,HttpServletResponse response);

    /**
     * 销毁session
     */
    public void destroySession();

}
