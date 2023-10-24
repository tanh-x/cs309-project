package com.kewargs.cs309.core.utils.backend.factory;

import static com.kewargs.cs309.core.utils.constants.UniversalConstants.USER_ENDPOINT;

import com.kewargs.cs309.core.utils.backend.request.JsonRequestCall;
import com.kewargs.cs309.core.utils.backend.request.PlainTextRequestCall;

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

    public static JsonRequestCall updateInfo(int id, String email, String displayName) {
        try {
            return RequestFactory.PUT()
                .url(USER_ENDPOINT + "update")
                .putBody("id", id + "")
                .putBody("email", email)
                .putBody("displayName", displayName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
