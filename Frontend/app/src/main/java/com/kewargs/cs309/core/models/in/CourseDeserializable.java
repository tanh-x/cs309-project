package com.kewargs.cs309.core.models.in;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public static ArrayList<CourseDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        int n = jsonArray.length();
        ArrayList<CourseDeserializable> courses = new ArrayList<>(n);
        for (int i = 0; i < n; i++) { courses.add(from(jsonArray.get(i).toString())); }
        return courses;
    }
}
