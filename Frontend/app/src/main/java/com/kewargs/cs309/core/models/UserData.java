package com.kewargs.cs309.core.models;

import org.json.JSONException;
import org.json.JSONObject;

public record UserData(
    int uid,
    String username,
    String email,
    String displayName,
    boolean isVerified
) implements DataModel<UserData> {
    @Override
    public UserData deserializeFrom(JSONObject json) throws JSONException {
        return new UserData(
            json.getInt("uid"),
            json.getString("username"),
            json.getString("email"),
            json.getString("displayName"),
            json.getBoolean("isVerified")
        );
    }
}
