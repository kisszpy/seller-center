package com.rrs.session.listener;

import com.rrs.session.CustomerSession;
import com.rrs.session.SessionManager;
import com.rrs.utils.ApplicationContextHolder;
import lombok.extern.java.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zhaop01 on 2015/4/8.
 */
@Log
public class SessionObserver implements Observer {


    @Override
    public void update(Observable o, Object arg) {
        SessionManager sessionManager = ApplicationContextHolder.getBean(SessionManager.class);
        CustomerSession session = (CustomerSession) o;
        sessionManager.updateSession(session);
    }
}
