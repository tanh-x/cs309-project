package com.kewargs.cs309.core.utils.backend.request;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonRequestCall extends AbstractRequest<JSONObject, JsonRequestCall> {
    JSONObject requestBody = new JSONObject();

    public JsonRequestCall(int method, String url) { super(method, url); }

    public JsonRequestCall putBody(String key, String value) throws JSONException {
        requestBody.put(key, value);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonObjectRequest build() {
        if (responseListener == null) {
            throw new IllegalArgumentException("You have not added a response listener to request object: " + this);
        }
        return new JsonObjectRequest(requestMethod, requestUrl, requestBody, responseListener, errorListener);
    }
}
