package com.kewargs.cs309.core.models.in;

import org.json.JSONException;
import org.json.JSONObject;

public record CourseDeserializable(
    int id,
    int programId,
    int num,
    String displayName
) {
    public static CourseDeserializable from(JSONObject json) throws JSONException {
        return new CourseDeserializable(
            json.getInt("id"),
            json.getInt("programId"),
            json.getInt("num"),
            json.getString("displayName")
        );
    }

    public static CourseDeserializable from(String serializedJson) throws JSONException {
        return from(new JSONObject(serializedJson));
    }
}
