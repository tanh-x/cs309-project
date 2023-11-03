package com.kewargs.cs309.core.utils.backend.factory;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import com.kewargs.cs309.core.utils.backend.request.PlainTextRequestCall;
import com.kewargs.cs309.core.utils.backend.request.JsonRequestCall;

public final class RequestFactory {
    public static PlainTextRequestCall GET() { return new PlainTextRequestCall(null); }

    public static PlainTextRequestCall GET(String url) { return new PlainTextRequestCall(url); }

    public static JsonRequestCall POST() { return new JsonRequestCall(POST, null); }

    public static JsonRequestCall POST(String url) { return new JsonRequestCall(POST, url); }

    public static JsonRequestCall PUT() { return new JsonRequestCall(PUT, null); }

    public static JsonRequestCall PUT(String url) { return new JsonRequestCall(PUT, url); }

}
