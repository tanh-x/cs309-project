package com.kewargs.cs309.activity.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint("SetTextI18n")
public class CourseListActivity extends AbstractActivity {
    public CourseListActivity() { super(R.layout.activity_course_list); }

    private EditText courseSearchField;
    private ImageButton searchButton;
    private LinearLayout courseList;
    private TextView debugText;

    private ArrayList<CourseDeserializable> courses;


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

                buildCourseListComponent();
            }).onError(error -> {
                debugText.setText("Error while fetching course information: " + error.toString());
            }).build());
    }


    private void buildCourseListComponent() {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        courses.stream()
            .map(course -> {
                View v = layoutInflater.inflate(R.layout.component_course_list, null);
                TextView idText = v.findViewById(R.id.courseIdentifierText);
                TextView nameText = v.findViewById(R.id.courseNameText);
                idText.setText("" + course.num());
                nameText.setText(course.displayName());
                return v;
            })
            .forEach(v -> courseList.addView(v));
    }


    private void searchButtonCallback(View view) {

    }


    @Override
    protected void collectElements() {
        courseSearchField = findViewById(R.id.courseSearchField);
        searchButton = findViewById(R.id.searchButton);
        courseList = findViewById(R.id.courseList);
        debugText = findViewById(R.id.debugText);
    }
}
