package com.kewargs.cs309.core.models.in;

import org.json.JSONException;
import org.json.JSONObject;

public record SeasonYearDeserializable(
    int season,
    int year
) {
    public static SeasonYearDeserializable from(JSONObject json) throws JSONException {
        return new SeasonYearDeserializable(
            json.getInt("season"),
            json.getInt("year")
        );
    }

    public static SeasonYearDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
