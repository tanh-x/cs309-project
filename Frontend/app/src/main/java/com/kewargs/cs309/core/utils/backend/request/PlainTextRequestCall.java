package com.kewargs.cs309.core.utils.backend.request;

import static com.android.volley.Request.Method.GET;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

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
        return new StringRequest(requestMethod, requestUrl, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return new HashMap<>() {{
                    if (bearerToken != null) put("Authorization", "Bearer " + bearerToken);
                }};
            }
        };
    }
}
