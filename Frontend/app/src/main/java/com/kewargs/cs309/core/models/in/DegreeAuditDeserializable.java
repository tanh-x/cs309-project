package com.kewargs.cs309.core.models.in;

import com.kewargs.cs309.core.utils.backend.request.DeserializationHelpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Function;

public record DegreeAuditDeserializable(
    int inProgressCredits,
    int appliedCredits,
    double gpa,
    SeasonYearDeserializable entryTerm,
    SeasonYearDeserializable graduationTerm,
    List<String> annotations,
    List<DegreeCourseEntryDeserializable> courses
) {
    public static DegreeAuditDeserializable from(JSONObject json) throws JSONException {
        return new DegreeAuditDeserializable(
            json.getInt("inProgressCredits"),
            json.getInt("appliedCredits"),
            json.getDouble("gpa"),
            SeasonYearDeserializable.from(json.getJSONObject("entryTerm")),
            SeasonYearDeserializable.from(json.getJSONObject("graduationTerm")),
            DeserializationHelpers.deserializeArray(json.getJSONArray("annotations"), Function.identity()),
            DegreeCourseEntryDeserializable.fromArray(json.getJSONArray("courses"))
        );
    }

    public static DegreeAuditDeserializable from(String serializedJson) {
        try {
            return from(new JSONObject(serializedJson));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
