package com.kewargs.cs309.core.manager;

class AuthenticationManager {
    String sessionToken = null;


    // ====== Housekeeping stuff ======
    private static AuthenticationManager instance = null;

    private AuthenticationManager() { }

    static synchronized AuthenticationManager getInstance() {
        if (instance == null) instance = new AuthenticationManager();
        return instance;
    }
}
