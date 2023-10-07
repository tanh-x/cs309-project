package com.kewargs.cs309.utils.backend.request;

import static com.android.volley.Request.Method.GET;

import com.android.volley.toolbox.StringRequest;

public class PlainTextRequestCall extends AbstractRequest<String, PlainTextRequestCall> {
    public PlainTextRequestCall(int method, String url) {
        super(method, url);
    }

    public PlainTextRequestCall(String url) {
        super(GET, url);
    }

    @Override
    @SuppressWarnings("unchecked")
    public StringRequest build() {
        return new StringRequest(requestMethod, requestUrl, responseListener, errorListener);
    }
}
