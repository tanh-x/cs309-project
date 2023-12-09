package com.kewargs.cs309.components;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseInfoActivity;
import com.kewargs.cs309.activity.dashboard.SchedulingActivity;
import com.kewargs.cs309.core.managers.SessionManager;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.utils.Course;

public final class CourseCardComponent extends InflatableComponent<ConstraintLayout> {
    private final CourseDeserializable course;
    private final AbstractActivity parentActivity;

    private TextView identifierText;
    private TextView nameText;
    private ImageButton infoButton;

    private ImageButton addButton;
    public CourseCardComponent(
        LayoutInflater inflater,
        AbstractActivity parentActivity,
        CourseDeserializable course
    ) {
        super(R.layout.component_course_card, inflater);
        this.parentActivity = parentActivity;
        this.course = course;
    }

    @Override
    protected ConstraintLayout render() {
        super.render();
        identifierText = findViewById(R.id.courseIdentifierText);
        nameText = findViewById(R.id.courseNameText);
        infoButton = findViewById(R.id.infoButton);
        addButton = findViewById(R.id.addCourseButton);

        infoButton.setOnClickListener(this::infoButtonCallback);
        addButton.setOnClickListener(this::addCourseButtonCallback);

        identifierText.setText(course.toString());
        nameText.setText(course.displayName());

        return stub;
    }

    private void addCourseButtonCallback(View view){
        //session.courseArr.add(course.id());
        SessionManager.courseQueue.add(new Course(course.id(), course.programIdentifier(), course.num(), null));
        showToast("Added Course!",parentActivity );
    }


    private void infoButtonCallback(View view) {
        Intent intent = new Intent(parentActivity, CourseInfoActivity.class);
        intent.putExtra("courseId", course.id());
        intent.putExtra("headerTitle", course.toString());
        parentActivity.startActivity(intent);
    }
    protected static void showToast(String content, Context context) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
