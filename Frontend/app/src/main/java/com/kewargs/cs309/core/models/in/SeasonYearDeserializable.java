package com.kewargs.cs309.core.models.in;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        String seasonText = "";
        switch (season) {
            case 0 -> seasonText = "Spring";
            case 1 -> seasonText = "Summer";
            case 2 -> seasonText = "Fall";
            case 3 -> seasonText = "Winter";
        }

        return seasonText + " " + year;
    }
}
