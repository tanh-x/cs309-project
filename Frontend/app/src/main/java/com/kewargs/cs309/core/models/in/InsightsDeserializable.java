package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableInt;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public record InsightsDeserializable(
    int id,
    int courseId,
    String summary,
    Double difficulty,
    String recommendProf,
    String tags
) {
    public static InsightsDeserializable from(JSONObject json) throws JSONException {
        return new InsightsDeserializable(
            json.getInt("id"),
            json.getInt("courseId"),
            json.getString("summary"),
            DeserializationHelpers.getNullableDouble(json, "difficulty"),
            json.getString("recommendProf"),
            json.getString("tags")
        );
    }

    public static InsightsDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<InsightsDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        return DeserializationHelpers.deserializeArray(jsonArray, InsightsDeserializable::from);
    }
}
