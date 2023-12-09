package com.kewargs.cs309.core.managers;

import android.content.Context;

import com.android.volley.Request;

import com.kewargs.cs309.core.utils.Course;
import com.kewargs.cs309.core.utils.backend.request.RequestCall;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public final class SessionManager {
    // ====== Authentication ======
    private AuthenticationManager authentication = null;


    public static ArrayList<Course> courseQueue = new ArrayList<>();
    public static ArrayList<Course> courseArrList = new ArrayList<>();

    public String getSessionToken() {
        if (authentication == null) return null;
        return authentication.getSessionToken();
    }

    public String getUsername() {
        if (authentication == null) return null;
        return authentication.getUsername();
    }

    public Integer getUserId() {
        if (authentication == null) return null;
        return authentication.getUserId();
    }

    public Boolean tokenExpired() { return authentication != null && authentication.getIsExpired(); }

    public boolean isLoggedIn() { return authentication != null && authentication.isLoggedIn(); }

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

    public synchronized <S, R extends RequestCall<S, ?>> void addRequest(RequestCall<S, R> request) {
        networkRequest.enqueue(request.build());
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
//        if (manager.isInitialized) throw new IllegalStateException("Do not reinitialize manager");

        // Get authentication singleton
        manager.authentication = AuthenticationManager.getInstance();

        // Get context manager pseudo-singleton
        manager.context = ContextManager.create(appContext);

        // Get Volley manager
        manager.networkRequest = NetworkRequestManager.getInstance().addContext(appContext);

        // Prevent reinitialization
        manager.isInitialized = true;

    }


    public synchronized void seppuku() {
        SessionManager manager = getInstance();
        manager.authentication = AuthenticationManager.renewInstance();
    }
}
