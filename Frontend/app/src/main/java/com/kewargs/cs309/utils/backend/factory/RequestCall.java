package com.kewargs.cs309.utils.backend.factory;

import com.android.volley.Request;
import com.android.volley.Response;

public interface RequestCall<S, R extends RequestCall<S, ?>> {

    R method(int method);

    R url(String url);

    R onResponse(Response.Listener<S> callback);

    R onError(Response.ErrorListener callback);

    <T> Request<T> build();
}
