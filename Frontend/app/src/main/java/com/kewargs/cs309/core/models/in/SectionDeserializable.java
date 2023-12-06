package com.kewargs.cs309.core.models.in;

import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableBoolean;
import static com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers.getNullableInt;

import android.util.Log;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Mirrors the SectionEnity class
 *
 * @author Thanh Mai
 */
public record SectionDeserializable(
    int id,
    int refNum,
    CourseDeserializable course,
    String section,
    int year,
    int season,
    Boolean isOnline,
    ArrayList<ScheduleDeserializable> schedules
) {
    public static SectionDeserializable from(JSONObject json) throws JSONException {
        return new SectionDeserializable(
            json.getInt("id"),
            json.getInt("refNum"),
            CourseDeserializable.from(json.getString("course")),
            json.getString("section"),
            json.getInt("year"),
            json.getInt("season"),
            getNullableBoolean(json, "isOnline"),
            ScheduleDeserializable.fromArray(json.getJSONArray("schedules"))
        );
    }

    public static SectionDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            Log.e("SectionDeserializable-bad-json", serializedJson);
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<SectionDeserializable> fromArray(JSONArray jsonArray) throws JSONException {
        return DeserializationHelpers.deserializeArray(jsonArray, SectionDeserializable::from);
    }
}
