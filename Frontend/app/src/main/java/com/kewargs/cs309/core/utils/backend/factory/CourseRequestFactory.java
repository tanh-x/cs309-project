package com.kewargs.cs309.core.utils.backend.factory;

import static com.kewargs.cs309.core.utils.constants.UniversalConstants.COURSE_ENDPOINT;

import com.kewargs.cs309.core.utils.backend.request.PlainTextRequestCall;

public class CourseRequestFactory {
    public static PlainTextRequestCall getAllCourseInformation() {
        return RequestFactory.GET().url(COURSE_ENDPOINT + "all/1");
    }
}
