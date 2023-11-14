package com.kewargs.cs309.core.utils.backend.request;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.kewargs.cs309.core.managers.SessionManager;

/**
 * Abstract base class for network requests with Volley, providing a template for request method,
 * URL, response handling, and error handling. It ensures token injection from the session
 * if available and provides a fluent interface for constructing requests.
 *
 * @param <ResponseType> The expected type of the response object.
 * @param <RequestType>  The type of the concrete class extending this abstract class.
 *
 * @author Thanh Mai
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRequest<ResponseType, RequestType extends AbstractRequest<ResponseType, RequestType>>
    implements RequestCall<ResponseType, RequestType> {
    /**
     * The HTTP request method (e.g., GET, POST).
     */
    protected int requestMethod;

    /**
     * The URL for the HTTP request.
     */
    protected String requestUrl;

    /**
     * Listener for successful responses.
     */
    protected Response.Listener<ResponseType> responseListener = null;

    /**
     * Listener for errors.
     */
    protected Response.ErrorListener errorListener = Throwable::printStackTrace;

    /**
     * Bearer token for authorization purposes, if available.
     */
    protected String bearerToken = null;

    /**
     * Constructs a new request with specified method and URL, injecting authorization token if available.
     *
     * @param method The HTTP method for the request.
     * @param url    The URL for the request.
     */
    protected AbstractRequest(int method, String url) {
        this.requestMethod = method;
        this.requestUrl = url;

        // Directly inject token if one is found in the session
        SessionManager session = SessionManager.getInstance();
        if (!session.isLoggedIn()) return;
        String token = session.getSessionToken();
        if (token == null) return;
        bearerToken = token;
    }

    /**
     * Sets the request method for the current request.
     *
     * @param method The HTTP method to be set.
     * @return The current instance with the updated method.
     */
    @Override
    public RequestType method(int method) {
        this.requestMethod = method;
        return (RequestType) this;
    }

    /**
     * Sets the URL for the current request.
     *
     * @param url The URL to be set.
     * @return The current instance with the updated URL.
     */
    @Override
    public RequestType url(String url) {
        this.requestUrl = url;
        return (RequestType) this;
    }

    /**
     * Sets the response listener for the current request.
     *
     * @param callback The listener to be invoked on a successful response.
     * @return The current instance with the updated response listener.
     */
    @Override
    public RequestType onResponse(Response.Listener<ResponseType> callback) {
        responseListener = callback;
        return (RequestType) this;
    }

    /**
     * Sets the error listener for the current request.
     *
     * @param callback The listener to be invoked on an error.
     * @return The current instance with the updated error listener.
     */
    @Override
    public RequestType onError(Response.ErrorListener callback) {
        errorListener = callback;
        return (RequestType) this;
    }

    /**
     * Sets the bearer token for the current request.
     *
     * @param token The bearer token for authorization.
     * @return The current instance with the updated bearer token.
     */
    public RequestType bearer(String token) {
        bearerToken = token;
        return (RequestType) this;
    }


    /**
     * Abstract method to build the actual Volley request.
     *
     * @param <T> The type of the expected response of the request.
     * @return The built Volley Request object.
     */
    abstract public <T> Request<T> build();


    /**
     * Provides a string representation of the request call, including the URL.
     *
     * @return A string representation of the request call.
     */
    @NonNull
    @Override
    public String toString() {
        return "<RequestCall to [ '" + requestUrl + "' ]>";
    }
}
