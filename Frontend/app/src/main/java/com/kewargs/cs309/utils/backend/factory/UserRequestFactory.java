package com.kewargs.cs309.utils.backend.factory;

import static com.kewargs.cs309.utils.UniversalConstants.USER_ENDPOINT;

import com.kewargs.cs309.utils.backend.factory.RequestFactory;
import com.kewargs.cs309.utils.backend.request.JsonRequestCall;
import com.kewargs.cs309.utils.backend.request.PlainTextRequestCall;

import org.json.JSONException;

public class UserRequestFactory {
    public static JsonRequestCall login(String email, String password) {
        try {
            return RequestFactory.POST()
                .url(USER_ENDPOINT + "login")
                .putBody("email", email)
                .putBody("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlainTextRequestCall getUserById(int id) {
        return RequestFactory.GET().url(USER_ENDPOINT + "id/" + id);
    }
}
