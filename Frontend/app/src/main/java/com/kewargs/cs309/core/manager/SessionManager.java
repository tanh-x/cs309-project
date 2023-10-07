package com.kewargs.cs309.core.manager;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public final class SessionManager {
    // ====== Authentication ======
    private AuthenticationManager authentication = null;

    public String getSessionToken() { return authentication.getSessionToken(); }

    public Integer userId() { return authentication.getUserId(); }

    public Boolean tokenExpired() { return authentication.getIsExpired(); }

    public boolean isLoggedIn() { return authentication.isLoggedIn(); }

    public void setSessionToken(String token) { authentication.setSessionToken(token); }

    public void setSessionFromLogin(JSONObject response) {
        try {
            setSessionToken((String) response.get("sessionJwt"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // ====== Android context ======
    private ContextManager context = null;

    // ====== Volley ======
    private NetworkRequestManager networkRequest = null;

    public synchronized <T> void addRequest(Request<T> request) {
        networkRequest.enqueue(request);
    }

    // ====== Housekeeping stuff ======
    private static SessionManager instance = null;
    private boolean isInitialized = false;

    private SessionManager() { }

    public static synchronized SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public static synchronized void initialize(Context appContext) {
        SessionManager manager = getInstance();
        if (manager.isInitialized) throw new IllegalStateException("Do not reinitialize manager");

        // Get authentication singleton
        manager.authentication = AuthenticationManager.getInstance();

        // Get context manager pseudo-singleton
        if (manager.context == null) manager.context = ContextManager.create(appContext);

        // Get Volley manager
        manager.networkRequest = NetworkRequestManager.getInstance().addContext(appContext);

        // Prevent reinitialization
        manager.isInitialized = true;
    }
}
