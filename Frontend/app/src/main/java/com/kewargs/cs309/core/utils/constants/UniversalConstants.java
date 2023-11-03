package com.kewargs.cs309.core.utils.constants;

public final class UniversalConstants {
    public static final String ENDPOINT = EndpointHostDefinition.guessEndpoint();
    public static final String WS_ENDPOINT = ENDPOINT.replace("https?", "ws");

    public static final String AUTH_ENDPOINT = ENDPOINT + "api/auth/";
    public static final String USER_ENDPOINT = ENDPOINT + "api/user/";
    public static final String STUDENT_ENDPOINT = ENDPOINT + "api/student/";
    public static final String COURSE_ENDPOINT = ENDPOINT + "api/course/";

    public static final String CHAT_ENDPOINT = WS_ENDPOINT + "chat/";

    public static final String AUDIT_UPLOAD_ENDPOINT = ENDPOINT+"api/reader/pdf/";
}
