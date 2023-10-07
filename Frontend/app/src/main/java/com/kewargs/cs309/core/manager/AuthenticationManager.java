package com.kewargs.cs309.core.manager;

import com.auth0.android.jwt.JWT;

final class AuthenticationManager {
    private final static int TOKEN_EXPIRATION_LEEWAY = 12;

    private String sessionToken = null;
    private Integer userId = null;
    private Boolean isExpired = null;

    String getSessionToken() { return sessionToken; }

    Integer getUserId() { return userId; }

    Boolean getIsExpired() { return isExpired; }

    boolean isLoggedIn() {
        return sessionToken != null
            && sessionToken.length() > 0
            && userId != null
            && !isExpired;
    }

    void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;

        JWT token = new JWT(this.sessionToken);
        userId = token.getClaim("userId").asInt();
        isExpired = token.isExpired(TOKEN_EXPIRATION_LEEWAY);
    }

    // ====== Housekeeping stuff ======
    private static AuthenticationManager instance = null;

    private AuthenticationManager() { }

    static synchronized AuthenticationManager getInstance() {
        if (instance == null) instance = new AuthenticationManager();
        return instance;
    }
}
