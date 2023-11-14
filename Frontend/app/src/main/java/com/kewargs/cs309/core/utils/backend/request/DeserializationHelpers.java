package com.kewargs.cs309.core.utils.backend.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Helper methods for deserializable classes
 *
 * @author Thanh Mai
 */
public class DeserializationHelpers {
    public static Integer getNullableInt(JSONObject json, String key) throws JSONException {
        return json.isNull(key) ? null : json.getInt(key);
    }

    public static Double getNullableDouble(JSONObject json, String key) throws JSONException {
        return json.isNull(key) ? null : json.getDouble(key);
    }

    public static Boolean getNullableBoolean(JSONObject json, String key) throws JSONException {
        return json.isNull(key) ? null : json.getBoolean(key);
    }

    public static <E> ArrayList<E> deserializeArray(JSONArray jsonArray, Function<String, E> from) throws JSONException {
        int n = jsonArray.length();
        ArrayList<E> sections = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            sections.add(from.apply(jsonArray.get(i).toString()));
        }
        return sections;
    }
}
