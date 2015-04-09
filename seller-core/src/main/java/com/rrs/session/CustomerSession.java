package com.rrs.session;

import com.google.common.collect.Maps;
import com.rrs.session.listener.SessionObserver;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import java.util.Observable;

/**
 * Created by zhaop01 on 2015/4/8.
 */
public class CustomerSession extends Observable implements HttpSession,Serializable {

    @Setter
    private  String id;
    @Setter
    private  long creationTime;
    @Setter
    private  long lastAccessedTime;

    private int maxInactiveInterval;
    @Setter
    @Getter
    private Map<String,Object> sessionContextMap;
    @Setter
    @Getter
    private boolean dirty;

    public CustomerSession(){
        this.addObserver(new SessionObserver());
    }

    public CustomerSession(CustomerHttpServletRequest request){
        this.id = SessionUtils.getSessionId(request);
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = System.currentTimeMillis();
        this.sessionContextMap = Maps.newConcurrentMap();
        this.addObserver(new SessionObserver());
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        this.maxInactiveInterval = i;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @JsonIgnore
    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getAttribute(String s) {
        return sessionContextMap.get(s);
    }

    @JsonIgnore
    @Override
    public Object getValue(String s) {
        return sessionContextMap.get(s);
    }

    @JsonIgnore
    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @JsonIgnore
    @Override
    public String[] getValueNames() {
       return sessionContextMap.keySet().toArray(new String[sessionContextMap.keySet().size()]);
    }

    @Override
    public void setAttribute(String s, Object o) {
        sessionContextMap.put(s,o);
        dirty = true;
        this.setChanged();
        this.notifyObservers(this);
    }

    @Override
    public void putValue(String s, Object o) {
        sessionContextMap.put(s,o);
        dirty = true;
        this.setChanged();
        this.notifyObservers(this);
    }

    @Override
    public void removeAttribute(String s) {
        sessionContextMap.remove(s);
        dirty = true;
        this.setChanged();
        this.notifyObservers(this);
    }

    @Override
    public void removeValue(String s) {
        sessionContextMap.remove(s);
        dirty = true;
        this.setChanged();
        this.notifyObservers(this);
    }

    @Override
    public void invalidate() {
        this.sessionContextMap = null;
        dirty = false;
        this.setChanged();
        this.notifyObservers(this);
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        if(!dirty){
            return true;
        }
        return false;
    }
}
