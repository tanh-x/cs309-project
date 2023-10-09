package com.kewargs.cs309.core.utils.backend.factory;

import com.kewargs.cs309.core.utils.backend.request.JsonRequestCall;
import com.kewargs.cs309.core.utils.backend.request.PlainTextRequestCall;
import com.kewargs.cs309.core.utils.constants.UniversalConstants;

import org.json.JSONException;

public class UserRequestFactory {
    public static JsonRequestCall login(String email, String password) {
        try {
            return RequestFactory.POST()
                .url(UniversalConstants.USER_ENDPOINT + "login")
                .putBody("email", email)
                .putBody("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlainTextRequestCall getUserById(int id) {
        return RequestFactory.GET().url(UniversalConstants.USER_ENDPOINT + "id/" + id);
    }
}
