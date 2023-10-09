package com.kewargs.cs309.core.utils.backend.request;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;

@SuppressWarnings("unchecked")
public abstract class AbstractRequest<S, R extends AbstractRequest<S, R>> implements RequestCall<S, R> {
    protected int requestMethod;
    protected String requestUrl;
    protected Response.Listener<S> responseListener = null;
    protected Response.ErrorListener errorListener = Throwable::printStackTrace;

    protected AbstractRequest(int method, String url) {
        this.requestMethod = method;
        this.requestUrl = url;
    }

    @Override
    public R method(int method) {
        this.requestMethod = method;
        return (R) this;
    }

    @Override
    public R url(String url) {
        this.requestUrl = url;
        return (R) this;
    }

    @Override
    public R onResponse(Response.Listener<S> callback) {
        responseListener = callback;
        return (R) this;
    }

    @Override
    public R onError(Response.ErrorListener callback) {
        errorListener = callback;
        return (R) this;
    }

    abstract public <T> Request<T> build();

    @NonNull
    @Override
    public String toString() {
        return "<RequestCall to [ '" + requestUrl + "' ]>";
    }
}
