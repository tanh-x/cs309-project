package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableBoolean;
import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableInt;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public record SectionDeserializable(
    int courseId,
    String section,
    int year,
    int season,
    Boolean isOnline,
    Integer startTime,
    Integer endTime,
    String location,
    String instructor
) {
    public static SectionDeserializable from(JSONObject json) throws JSONException {
        return new SectionDeserializable(
            json.getInt("courseId"),
            json.getString("section"),
            json.getInt("year"),
            json.getInt("season"),
            getNullableBoolean(json, "isOnline"),
            getNullableInt(json, "startTime"),
            getNullableInt(json, "endTime"),
            json.getString("location"),
            json.getString("instructor")
        );
    }

    public static SectionDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<SectionDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        return DeserializationHelpers.deserializeArray(jsonArray, SectionDeserializable::from);
    }
}
