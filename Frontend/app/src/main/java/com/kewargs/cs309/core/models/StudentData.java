package com.kewargs.cs309.core.models;

import org.json.JSONException;
import org.json.JSONObject;

public record StudentData(
    int uid,
    String username,
    String email,
    String displayName,
    boolean isVerified,
    Integer primaryMajor
) implements DataModel<StudentData> {
    @Override
    public StudentData deserializeFrom(JSONObject json) throws JSONException {
        Integer primaryMajor = null;
        try { primaryMajor = json.getInt("primaryMajor"); } catch (JSONException ignored) { }

        return new StudentData(
            json.getInt("uid"),
            json.getString("username"),
            json.getString("email"),
            json.getString("displayName"),
            json.getBoolean("isVerified"),
            primaryMajor
        );
    }
}


