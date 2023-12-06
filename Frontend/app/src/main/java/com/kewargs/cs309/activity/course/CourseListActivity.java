package com.kewargs.cs309.activity.course;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static com.kewargs.cs309.core.utils.Helpers.toQuantifierPattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.components.CourseCardComponent;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class CourseListActivity extends AbstractActivity {
    public CourseListActivity() { super(R.layout.activity_course_list); }

    private EditText courseSearchField;
    private ImageButton searchButton;
    private LinearLayout courseList;
    private TextView debugText;

    private String searchPattern = "";

    private ArrayList<CourseDeserializable> courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchButton.setOnClickListener(this::searchButtonCallback);

        courseSearchField.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                searchButtonCallback(v);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        session.addRequest(CourseRequestFactory.getAllCourseInformation()
                .onResponse(response -> {
                    try {
                        debugText.setText("End of list");
                        courses = CourseDeserializable.fromArray(new JSONArray(response));
                    } catch (JSONException e) {
                        debugText.setText("Error while fetching course information. " + e);
                    }
                    buildCourseListComponent();
                }).onError(error -> {
                    debugText.setText("Error while fetching course information: " + error.toString());
                })
                .build()
        );
    }


    private void buildCourseListComponent() {
        // Clear out all children
        courseList.removeAllViews();

        // Make an inflater
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        courses.stream()  // ArrayList<CourseDeserializable>
            .filter(this::searchPatternMatches) // Only get those that matches the search
            .limit(100)  // Limit to the first 100 courses
            .map(course -> new CourseCardComponent(layoutInflater, this, course)) // Build into components
            .forEach(component -> component.bindTo(courseList)); // Add to view
    }

    private boolean searchPatternMatches(CourseDeserializable course) {
        if (searchPattern.length() == 0) return true;
        return course.toQuantifierString().contains(searchPattern);
    }


    private void searchButtonCallback(View view) {
        searchPattern = toQuantifierPattern(courseSearchField.getText().toString());
        buildCourseListComponent();
    }

    @Override
    protected void collectElements() {
        courseSearchField = findViewById(R.id.courseSearchField);
        searchButton = findViewById(R.id.searchButton);
        courseList = findViewById(R.id.courseList);
        debugText = findViewById(R.id.debugText);
    }
}
