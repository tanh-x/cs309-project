package com.kewargs.cs309.core.utils.backend.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JsonRequestCall extends AbstractRequest<String, JsonRequestCall> {
    JSONObject requestBody = new JSONObject();

    public JsonRequestCall(int method, String url) { super(method, url); }

    public JsonRequestCall putBody(String key, String value) throws JSONException {
        requestBody.put(key, value);
        return this;
    }

    @Override
    public JsonRequestCall onResponse(Response.Listener<String> callback) {
        return super.onResponse(callback);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Request<String> build() {
        if (responseListener == null) {
            throw new IllegalArgumentException("You have not added a response listener to request object: " + this);
        }

        return new StringRequest(requestMethod, requestUrl, responseListener, errorListener) {
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
