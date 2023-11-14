package com.kewargs.cs309.core.models.in;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Mirrors the UserData class
 *
 * @author Thanh Mai
 */
public record UserDeserializable(
    int uid,
    String username,
    String email,
    String displayName,
    boolean isVerified
) {
    public static UserDeserializable from(JSONObject json) throws JSONException {
        return new UserDeserializable(
            json.getInt("uid"),
            json.getString("username"),
            json.getString("email"),
            json.getString("displayName"),
            json.getBoolean("isVerified")
        );
    }

    public static UserDeserializable from(String serializedJson) throws JSONException {
        return from(new JSONObject(serializedJson));
    }
}
