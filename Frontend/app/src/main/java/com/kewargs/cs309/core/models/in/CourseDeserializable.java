package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.Helpers.toQuantifierPattern;
import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableBoolean;

import androidx.annotation.NonNull;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public record CourseDeserializable(
    int id,
    String programIdentifier,
    int num,
    String displayName,
    String description,
    int credits,
    boolean isVariableCredit,
    Boolean springOffered,
    Boolean summerOffered,
    Boolean fallOffered,
    Boolean winterOffered,
    Boolean isGraded
) {
    public static CourseDeserializable from(JSONObject json) {
        try {
            return new CourseDeserializable(
                json.getInt("id"),
                json.getString("programIdentifier"),
                json.getInt("num"),
                json.getString("displayName"),
                json.getString("description"),
                json.getInt("credits"),
                json.getBoolean("isVariableCredit"),
                getNullableBoolean(json, "springOffered"),
                getNullableBoolean(json, "summerOffered"),
                getNullableBoolean(json, "fallOffered"),
                getNullableBoolean(json, "winterOffered"),
                getNullableBoolean(json, "isGraded")
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static CourseDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<CourseDeserializable> fromArray(JSONArray jsonArray) {
        try {
            return DeserializationHelpers.deserializeArray(jsonArray, CourseDeserializable::from);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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
