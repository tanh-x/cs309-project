package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableInt;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Mirrors the ScheduleEntity class
 *
 * @author Thanh Mai
 */
public record ScheduleDeserializable(
    int sectionId,
    Integer startTime,
    Integer endTime,
    String location,
    String instructor,
    String instructionType
) {
    public static ScheduleDeserializable from(JSONObject json) throws JSONException {
        return new ScheduleDeserializable(
            json.getInt("sectionId"),
            getNullableInt(json, "startTime"),
            getNullableInt(json, "endTime"),
            json.getString("location"),
            json.getString("instructor"),
            json.getString("instructionType")
        );
    }

    public static ScheduleDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ScheduleDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        return DeserializationHelpers.deserializeArray(jsonArray, ScheduleDeserializable::from);
    }
}
