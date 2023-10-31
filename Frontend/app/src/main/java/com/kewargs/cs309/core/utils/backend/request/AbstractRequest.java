package com.kewargs.cs309.core.utils.backend.request;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;

@SuppressWarnings("unchecked")
public abstract class AbstractRequest<ResponseType, RequestType extends AbstractRequest<ResponseType, RequestType>>
    implements RequestCall<ResponseType, RequestType> {
    protected int requestMethod;
    protected String requestUrl;
    protected Response.Listener<ResponseType> responseListener = null;
    protected Response.ErrorListener errorListener = Throwable::printStackTrace;

    protected String bearerToken = null;

    protected AbstractRequest(int method, String url) {
        this.requestMethod = method;
        this.requestUrl = url;
    }

    @Override
    public RequestType method(int method) {
        this.requestMethod = method;
        return (RequestType) this;
    }

    @Override
    public RequestType url(String url) {
        this.requestUrl = url;
        return (RequestType) this;
    }

    @Override
    public RequestType onResponse(Response.Listener<ResponseType> callback) {
        responseListener = callback;
        return (RequestType) this;
    }

    @Override
    public RequestType onError(Response.ErrorListener callback) {
        errorListener = callback;
        return (RequestType) this;
    }

    public RequestType bearer(String token) {
        bearerToken = token;
        return (RequestType) this;
    }

    abstract public <T> Request<T> build();

    @NonNull
    @Override
    public String toString() {
        return "<RequestCall to [ '" + requestUrl + "' ]>";
    }
}
