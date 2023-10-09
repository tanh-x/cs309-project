package com.kewargs.cs309.core.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface DeserializableModel<T extends DeserializableModel<?>> {
    T deserializeFrom(JSONObject json) throws JSONException;

    default T deserializeFrom(String seralizedJson) {
        return deserializeFrom(seralizedJson);
    }
}
