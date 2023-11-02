package com.kewargs.cs309.activity.course;

import static com.kewargs.cs309.core.utils.Helpers.boolToYesNo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.models.in.SectionDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Must pass in a courseId
 */
public final class CourseInfoActivity extends AbstractActivity {
    public CourseInfoActivity() { super(R.layout.activity_course_info); }

    private ImageButton backButton;
    private TextView headerTitle;

    private Integer courseId = null;
    private CourseDeserializable course = null;
    private Collection<SectionDeserializable> sections = null;

    private TextView titleText;
    private TextView descriptionText;
    private TextView creditsText;
    private TextView variableCreditsText;
    private TextView deliveryText;
    private TextView gradedText;
    private TextView seasonsText;

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
                course = CourseDeserializable.from(response);
                buildCourseInfoComponents();
            })
            .onError(error -> {
                showToast("Couldn't get course info.", this);
                finish();
            })
            .build());

        session.addRequest(CourseRequestFactory.getCourseSections(courseId)
            .onResponse(response -> {
                try {
                    CourseDeserializable.fromArray(new JSONArray(response));
                    buildSectionComponents();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            })
            .onError(error -> {
                showToast("Couldn't get schedule info.", this);
                finish();
            })
            .build()
        );
    }

    @SuppressLint("SetTextI18n")
    private void buildCourseInfoComponents() {
        if (course == null) return;

        titleText.setText(course.toString() + ": " + course.displayName());
        descriptionText.setText(course.description());
        creditsText.setText("" + course.credits());
        variableCreditsText.setText(boolToYesNo(course.isVariableCredit()));
        gradedText.setText(boolToYesNo(course.isGraded()));
        deliveryText.setText("In-person");

        seasonsText.setText(String.join(" · ", new ArrayList<>() {{
            if (Boolean.TRUE.equals(course.springOffered())) add("Spring");
            if (Boolean.TRUE.equals(course.summerOffered())) add("Summer");
            if (Boolean.TRUE.equals(course.fallOffered())) add("Fall");
            if (Boolean.TRUE.equals(course.winterOffered())) add("Winter");
        }}));
    }

    private void buildSectionComponents() {
        if (sections == null) return;


    }

    @Override
    protected void collectElements() {
        backButton = findViewById(R.id.backButton);
        headerTitle = findViewById(R.id.headerTitle);

        titleText = findViewById(R.id.titleText);
        descriptionText = findViewById(R.id.descriptionText);
        creditsText = findViewById(R.id.creditsText);
        variableCreditsText = findViewById(R.id.variableCreditsText);
        deliveryText = findViewById(R.id.deliveryText);
        gradedText = findViewById(R.id.gradedText);
        seasonsText = findViewById(R.id.seasonsText);
    }
}
