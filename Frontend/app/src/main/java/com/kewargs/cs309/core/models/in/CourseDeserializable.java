package com.kewargs.cs309.core.models.in;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public record CourseDeserializable(
    int id,
    String programIdentifier,
    int num,
    String displayName
) {
    public static CourseDeserializable from(JSONObject json) throws JSONException {
        return new CourseDeserializable(
            json.getInt("id"),
            json.getString("programIdentifier"),
            json.getInt("num"),
            json.getString("displayName")
        );
    }

    public static CourseDeserializable from(String serializedJson) throws JSONException {
        return from(new JSONObject(serializedJson));
    }

    public static CourseDeserializable[] fromArray(JSONArray jsonArray) throws JSONException {
        int n = jsonArray.length();
        CourseDeserializable[] courses = new CourseDeserializable[n];
        for (int i = 0; i < n; i++) { courses[i] = from(jsonArray.get(i).toString()); }
        return courses;
    }
}
