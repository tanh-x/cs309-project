package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableInt;

import org.json.JSONException;
import org.json.JSONObject;

public record StudentDeserializable(
    int uid,
    String username,
    String email,
    String displayName,
    boolean isVerified,
    Integer primaryMajor
) {
    public static StudentDeserializable from(JSONObject json) throws JSONException {
        return new StudentDeserializable(
            json.getInt("uid"),
            json.getString("username"),
            json.getString("email"),
            json.getString("displayName"),
            json.getBoolean("isVerified"),
            getNullableInt(json, "primaryMajor")
        );
    }

    public static StudentDeserializable from(String serializedJson) throws JSONException {
        return from(new JSONObject(serializedJson));
    }
}


