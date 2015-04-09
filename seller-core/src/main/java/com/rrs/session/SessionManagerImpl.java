package com.rrs.session;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rrs.utils.KeyUtils;
import com.rrs.seller.core.base.RedisBaseDao;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop01 on 2015/4/8.
 */
@Component
public class SessionManagerImpl extends RedisBaseDao<CustomerSession> implements SessionManager {


    private static final int ALIVE_TIME_OUT = 300;


    private final LoadingCache<String,Object> cache;


    public SessionManagerImpl(){

        this.cache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build(new CacheLoader<String, Object>() {
            @Override
            public Object load(String sessionId) throws Exception {
                return SessionManagerImpl.this.getEntity(KeyUtils.redisSessionValue(sessionId));
            }
        });
    }

    @Override
    public void updateExpire(String key) {
        this.setExpiredTime(key,ALIVE_TIME_OUT);
    }

    @Override
    public void updateSession(CustomerSession session) {
        Assert.notNull(session,"this session is not null !");
        this.set(KeyUtils.redisSessionValue(session.getId()),session,ALIVE_TIME_OUT);
    }

    @Override
    public HttpSession createSession(CustomerHttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;
        for(Cookie cookie : cookies){
            if(KeyUtils.getCookieKey().equals(cookie.getName())){
                sessionId = cookie.getValue();
                break;
            }
        }
        if(StringUtils.isEmpty(sessionId)){
            CustomerSession session =   new CustomerSession(request);
            this.set(KeyUtils.redisSessionKey(session.getId()), session.getId(), ALIVE_TIME_OUT);
            this.set(KeyUtils.redisSessionValue(session.getId()),session,ALIVE_TIME_OUT);
            // this.setHash(KeyUtils.redisSessionValue(session.getId()),session,ALIVE_TIME_OUT);
            Cookie cookie = new Cookie(KeyUtils.getCookieKey(),session.getId());
            // cookie.setDomain("domain");
            // cookie.setMaxAge(ALIVE_TIME_OUT);
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            return session;
        }else{
            return getCurrentSessionAndNew(sessionId,request);
        }
    }

    private CustomerSession getCurrentSessionAndNew(String sessionId,CustomerHttpServletRequest request){
        if(getCurrentSession(sessionId)==null){
            CustomerSession session =   new CustomerSession(request);
            session.setId(sessionId);
            this.set(KeyUtils.redisSessionKey(session.getId()), session.getId(), ALIVE_TIME_OUT);
            this.set(KeyUtils.redisSessionValue(session.getId()),session,ALIVE_TIME_OUT);
            return  session;
        }else{
            return getCurrentSession(sessionId);
        }
    }

    private CustomerSession getCurrentSession(String sessionId) {
        try{
            return (CustomerSession) this.cache.getUnchecked(sessionId);
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public void destroySession() {

    }
}
