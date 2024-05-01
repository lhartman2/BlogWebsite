package org.example.blogwebsite;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionIdListener;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener {

    @Override
    public void sessionIdChanged(HttpSessionEvent httpSessionEvent, String oldId) {
        System.out.println(oldId + " changed to " + httpSessionEvent.getSession().getId());
        SessionListUtil.updateSessionId(httpSessionEvent.getSession(), oldId);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println(se.getSession().getId() + " created");
        SessionListUtil.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println(se.getSession().getId() + " destroyed");
        SessionListUtil.removeSession(se.getSession());
    }
}
