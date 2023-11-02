package com.kewargs.cs309.activity.course;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONException;

/**
 * Must pass in a courseId
 */
public final class CourseInfoActivity extends AbstractActivity {
    public CourseInfoActivity() { super(R.layout.activity_course_info); }

    private ImageButton backButton;
    private TextView headerTitle;

    private Integer courseId = null;
    private CourseDeserializable course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get selected courseId from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras == null) throw new IllegalStateException("No courseData for CourseInfoActivity");
        courseId = extras.getInt("courseId");
        headerTitle.setText(extras.getString("headerTitle"));

        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();

        session.addRequest(CourseRequestFactory.getCourseInfo(courseId)
            .onResponse(response -> {
                try {
                    course = CourseDeserializable.from(response);
                    buildCourseInfoComponents();
                } catch (JSONException e) {
                    showToast("Invalid response from server", this);
                }
            })
            .onError(error -> {
                showToast("Couldn't get course info.", this);
                finish();
            })
            .bearer(session.getSessionToken())
            .build());
    }

    private void buildCourseInfoComponents() {

    }

    private void buildSectionComponents() {

    }

    @Override
    protected void collectElements() {
        backButton = findViewById(R.id.backButton);
        headerTitle = findViewById(R.id.headerTitle);
    }
}
