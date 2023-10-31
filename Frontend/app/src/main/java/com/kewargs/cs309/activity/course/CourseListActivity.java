package com.kewargs.cs309.activity.course;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

@SuppressLint("SetTextI18n")
public class CourseListActivity extends AbstractActivity {
    public CourseListActivity() { super(R.layout.activity_course_list); }

    private EditText courseSearchField;
    private ImageButton searchButton;
    private ScrollView courseScroll;
    private TextView debugText;

    private CourseDeserializable[] courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchButton.setOnClickListener(this::searchButtonCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();

        session.addRequest(CourseRequestFactory.getAllCourseInformation()
            .onResponse(response -> {
                try {
                    debugText.setText(response);
                    courses = CourseDeserializable.fromArray(new JSONArray(response));
                } catch (JSONException e) {
                    debugText.setText("Error while fetching course information. " + e);
                }
            })
            .onError(error -> {
                debugText.setText("Error while fetching course information: " + error.toString());
            })
            .bearer(session.getSessionToken())
            .build()
        );
    }


    private void searchButtonCallback(View view) {

    }


    @Override
    protected void collectElements() {
        courseSearchField = findViewById(R.id.courseSearchField);
        searchButton = findViewById(R.id.searchButton);
        courseScroll = findViewById(R.id.courseScroll);
        debugText = findViewById(R.id.debugText);
    }
}
