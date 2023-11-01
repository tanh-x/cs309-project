package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.Helpers.toQuantifierPattern;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public static ArrayList<CourseDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        int n = jsonArray.length();
        ArrayList<CourseDeserializable> courses = new ArrayList<>(n);
        for (int i = 0; i < n; i++) { courses.add(from(jsonArray.get(i).toString())); }
        return courses;
    }

    public String toQuantifierString() {
        return toQuantifierPattern(this.toString() + this.displayName);
    }

    @NonNull
    @Override
    public String toString() {
        return this.programIdentifier + " " + this.num;
    }
}
