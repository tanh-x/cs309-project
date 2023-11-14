package com.kewargs.cs309.core.utils.backend.request;

import static com.android.volley.Request.Method.GET;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A concrete implementation of AbstractRequest for plain text responses. It extends the capabilities
 * of AbstractRequest to return a StringRequest, which is suitable for simple requests expecting
 * a string response.
 *
 * @author Thanh Mai
 */
public class PlainTextRequestCall extends AbstractRequest<String, PlainTextRequestCall> {
    /**
     * Constructor for PlainTextRequestCall with specified method and URL.
     *
     * @param method The HTTP method for the request.
     * @param url    The URL for the request.
     */
    public PlainTextRequestCall(int method, String url) {
        super(method, url);
    }

    /**
     * Constructor for PlainTextRequestCall with default method GET and specified URL.
     *
     * @param url The URL for the request.
     */
    public PlainTextRequestCall(String url) {
        super(GET, url);
    }

    /**
     * Builds a StringRequest with preset method, URL, response listener, and error listener.
     * Overrides getHeaders to inject Authorization header if a bearer token is available.
     *
     * @return The constructed StringRequest object ready for dispatch.
     */
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
