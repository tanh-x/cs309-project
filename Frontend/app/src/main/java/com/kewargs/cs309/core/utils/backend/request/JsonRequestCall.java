package com.kewargs.cs309.core.utils.backend.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * A concrete implementation of {@link AbstractRequest} for JSON requests. This class allows for
 * building a Volley StringRequest with a JSON body. It includes methods for setting the request
 * body and building the actual request to be sent over the network.
 */
public class JsonRequestCall extends AbstractRequest<String, JsonRequestCall> {
    /**
     * JSON object to hold the body of the post request.
     */
    private final JSONObject requestBody = new JSONObject();

    /**
     * Constructor for JsonRequestCall.
     *
     * @param method The HTTP method for the request.
     * @param url    The URL for the request.
     */
    public JsonRequestCall(int method, String url) { super(method, url); }

    /**
     * Adds a key-value pair to the JSON request body.
     *
     * @param key   The key as a String.
     * @param value The value as a String.
     * @return This JsonRequestCall object to allow for method chaining.
     * @throws JSONException if the key or value are invalid.
     */
    public JsonRequestCall putBody(String key, String value) throws JSONException {
        requestBody.put(key, value);
        return this;
    }

    /**
     * Overrides the onResponse method to return an instance of this class.
     *
     * @param callback The callback for response handling.
     * @return This JsonRequestCall object to allow for method chaining.
     */
    @Override
    public JsonRequestCall onResponse(Response.Listener<String> callback) {
        return super.onResponse(callback);
    }

    /**
     * Builds the Volley StringRequest with the current configuration.
     * Throws IllegalArgumentException if the response listener is not set.
     *
     * @return The built Volley Request object.
     * @throws IllegalArgumentException if response listener is not set.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Request<String> build() {
        if (responseListener == null) {
            throw new IllegalArgumentException("You have not added a response listener to request object: " + this);
        }

        return new StringRequest(requestMethod, requestUrl, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return new HashMap<>() {{
                    if (bearerToken != null) put("Authorization", "Bearer " + bearerToken);
                }};
            }

            @Override
            public byte[] getBody() {
                return requestBody.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
    }
}
