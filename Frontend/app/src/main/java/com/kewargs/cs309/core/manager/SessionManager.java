package com.kewargs.cs309.core.manager;

import android.content.Context;

public class SessionManager {
    // ====== Authentication ======
    private AuthenticationManager authentication = null;

    public String getSessionToken() { return authentication.sessionToken; }

    public void setSessionToken(String token) { authentication.sessionToken = token; }

    // ====== Android context ======
    private ContextManager context = null;

    // ====== Volley ======
    private NetworkRequestManager networkRequest = null;


    // ====== Housekeeping stuff ======
    private static SessionManager instance = null;
    private boolean isInitialized = false;

    private SessionManager() { }

    public static synchronized SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public static synchronized SessionManager initialize(Context appContext) {
        SessionManager manager = getInstance();
        if (manager.isInitialized) throw new IllegalStateException("Do not reinitialize manager");

        // Get authentication singleton
        manager.authentication = AuthenticationManager.getInstance();

        // Get context manager pseudo-singleton
        if (manager.context == null) manager.context = ContextManager.create(appContext);

        // Get Volley manager
        manager.networkRequest = NetworkRequestManager.getInstance();

        // Prevent reinitialization
        manager.isInitialized = true;

        return manager;
    }
}
