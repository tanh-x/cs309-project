package com.kewargs.cs309.core.models;

import org.json.JSONException;
import org.json.JSONObject;

public record UserDeserializable(
    int uid,
    String username,
    String email,
    String displayName,
    boolean isVerified
) implements DeserializableModel<UserDeserializable> {
    @Override
    public UserDeserializable deserializeFrom(JSONObject json) throws JSONException {
        return new UserDeserializable(
            json.getInt("uid"),
            json.getString("username"),
            json.getString("email"),
            json.getString("displayName"),
            json.getBoolean("isVerified")
        );
    }
}
