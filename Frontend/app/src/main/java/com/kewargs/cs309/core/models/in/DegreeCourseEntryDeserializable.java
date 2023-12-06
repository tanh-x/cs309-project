package com.kewargs.cs309.core.models.in;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public record DegreeCourseEntryDeserializable(
    String program,
    String num,
    SeasonYearDeserializable term,
    String grade,
    int credits,
    String annotations
) {
    public static DegreeCourseEntryDeserializable from(JSONObject json) throws JSONException {
        return new DegreeCourseEntryDeserializable(
            json.getString("program"),
            json.getString("num"),
            SeasonYearDeserializable.from(json.getJSONObject("term")),
            json.getString("grade"),
            json.getInt("credits"),
            json.getString("annotation")
        );
    }


    public static DegreeCourseEntryDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<DegreeCourseEntryDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        return DeserializationHelpers.deserializeArray(jsonArray, DegreeCourseEntryDeserializable::from);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DegreeCourseEntryDeserializable that = (DegreeCourseEntryDeserializable) o;
        return Objects.equals(program, that.program) && Objects.equals(num, that.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(program, num);
    }
}

