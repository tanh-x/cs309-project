package com.kewargs.cs309.core.utils.constants;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class EndpointHostDefinition {
    /**
     * Loopback address to the actual machine, whereas 127.0.0.1 refers to the device's loopback.
     */
    static final String LOCAL_ENDPOINT = "http://10.0.2.2:8080/";

    /**
     * SSH reverse tunneling endpoint of VM_ENDPOINT, allows remote access.
     */
    static final String REVERSE_TUNNEL_ENDPOINT = "http://cs309.kewargs.com:8080/";

    /**
     * Original address pointing to the VM, can only be accessed within the school's local network.
     */
    static final String VM_ENDPOINT = "http://coms-309-029.class.las.iastate.edu:8080/";

    /**
     * Gets the first available endpoint, by priority. For dev environment only.
     *
     * @return The first available endpoint
     */
    static String guessEndpoint() {
        String[] endpoints = {
            LOCAL_ENDPOINT,
            VM_ENDPOINT,
            REVERSE_TUNNEL_ENDPOINT
        };
        for (String endpoint : endpoints) {
            try {
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(1232);
                int responseCode = connection.getResponseCode();
                Log.e("AAAAAA", endpoint + " - " + responseCode + "");
                if (responseCode >= 200 && responseCode < 300) return endpoint;
            } catch (IOException ignored) { }
        }

        return VM_ENDPOINT;
    }
}
