package com.kewargs.cs309.activity.course;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;

public class CourseListActivity extends AbstractActivity {
    public CourseListActivity() { super(R.layout.activity_course_list); }

    private EditText courseSearchField;
    private ImageButton searchButton;
    private ScrollView courseScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchButton.setOnClickListener(this::searchButtonCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void searchButtonCallback(View view) {

    }


    @Override
    protected void collectElements() {
        courseSearchField = findViewById(R.id.courseSearchField);
        searchButton = findViewById(R.id.searchButton);
        courseScroll = findViewById(R.id.courseScroll);
    }
}
