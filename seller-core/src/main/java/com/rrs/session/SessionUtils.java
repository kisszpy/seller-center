package com.rrs.session;

import com.google.common.hash.Hashing;
import com.rrs.constants.Constants;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by zhaop01 on 2015/4/8.
 */
public class SessionUtils {

    /**
     * 获取本远程IP地址
     * @param request
     * @return
     */
    public static String getRemoteIPAddress(CustomerHttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;

    }

    public static String getUUID(){
       return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 生成SessionID算法
     * @param request
     * @return
     */
    public static String getSessionId(CustomerHttpServletRequest request){
        String hashRemoteAddress = Hashing.md5().hashString(getRemoteIPAddress(request), Charset.forName(Constants.DEFAULT_CHAR_SET)).toString();
        String hashLocalAddress = Hashing.md5().hashString(request.getLocalAddr(),Charset.forName(Constants.DEFAULT_CHAR_SET)).toString();
       return hashRemoteAddress.substring(0,8)+hashLocalAddress.substring(0,8)+getUUID().substring(16,32);
    }

}
