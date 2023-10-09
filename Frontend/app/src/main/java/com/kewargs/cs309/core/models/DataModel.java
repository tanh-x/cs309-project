package com.kewargs.cs309.core.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface DataModel<T extends DataModel<?>> {
    T deserializeFrom(JSONObject json) throws JSONException;

    default T deserializeFrom(String seralizedJson) {
        return deserializeFrom(seralizedJson);
    }
}
