package com.kewargs.cs309.core.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

class NetworkRequestManager {
    private RequestQueue requestQueue = null;

    synchronized void enqueue(Request<?> request) {

    }



    // ====== Housekeeping stuff ======
    private static NetworkRequestManager instance = null;

    private NetworkRequestManager() { }

    static synchronized NetworkRequestManager getInstance() {
        if (instance == null) instance = new NetworkRequestManager();
        return instance;
    }

    synchronized void addContext(Context providedContext) {
        if (requestQueue != null) throw new IllegalStateException("Volley already has context");
        requestQueue = Volley.newRequestQueue(providedContext.getApplicationContext());
    }
}
