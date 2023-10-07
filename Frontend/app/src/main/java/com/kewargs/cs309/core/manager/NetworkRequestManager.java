package com.kewargs.cs309.core.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

final class NetworkRequestManager {
    private RequestQueue requestQueue = null;

    synchronized <T> void enqueue(Request<T> request) {
        if (requestQueue == null) throw new IllegalStateException("Volley queue not instantiated");
        Log.i("NetworkRequestManager", "Making request to " + request.getUrl());
        requestQueue.add(request);
    }


    // ====== Housekeeping stuff ======
    private static NetworkRequestManager instance = null;

    private NetworkRequestManager() { }

    static synchronized NetworkRequestManager getInstance() {
        if (instance == null) instance = new NetworkRequestManager();
        return instance;
    }

    synchronized NetworkRequestManager addContext(Context providedContext) {
        if (requestQueue != null) throw new IllegalStateException("Volley already has context");
        requestQueue = Volley.newRequestQueue(providedContext.getApplicationContext());
        return this;
    }
}
