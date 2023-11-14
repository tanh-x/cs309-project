package com.kewargs.cs309.core.utils.backend.factory;

import static com.kewargs.cs309.core.utils.constants.UniversalConstants.COURSE_ENDPOINT;

import com.kewargs.cs309.core.utils.backend.request.PlainTextRequestCall;

/**
 * Builds requests pertaining to the course endpoint
 *
 * @author Thanh Mai
 */
public class CourseRequestFactory {
    public static PlainTextRequestCall getAllCourseInformation() {
        return RequestFactory.GET().url(COURSE_ENDPOINT + "all/1");
    }

    public static PlainTextRequestCall getCourseInfo(int courseId) {
        return RequestFactory.GET().url(COURSE_ENDPOINT + courseId);
    }

    public static PlainTextRequestCall getCourseSections(int courseId) {
        return RequestFactory.GET().url(COURSE_ENDPOINT + "sections/" + courseId);
    }

    public static PlainTextRequestCall getCourseInsights(int courseId) {
        return RequestFactory.GET().url(COURSE_ENDPOINT + "insights/" + courseId);
    }
}
