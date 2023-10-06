package com.kewargs.cs309.core;

public class SessionManager {
    private static SessionManager instance = null;
    private String sessionToken = null;

    private SessionManager() {

    }

    public String getSessionToken() { return sessionToken; }

    public void setSessionToken(String token) { sessionToken = token; }

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }
}
