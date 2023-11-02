package com.kewargs.cs309.components;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseInfoActivity;
import com.kewargs.cs309.core.models.in.CourseDeserializable;

public final class CourseCardComponent extends InflatableComponent<ConstraintLayout> {
    private final CourseDeserializable course;
    private final AbstractActivity parentActivity;

    private TextView identifierText;
    private TextView nameText;
    private ImageButton infoButton;

    public CourseCardComponent(
        LayoutInflater inflater,
        AbstractActivity parentActivity,
        CourseDeserializable course
    ) {
        super(R.layout.component_course_list, inflater);
        this.parentActivity = parentActivity;
        this.course = course;
    }

    @Override
    protected ConstraintLayout render() {
        super.render();
        identifierText = findViewById(R.id.courseIdentifierText);
        nameText = findViewById(R.id.courseNameText);
        infoButton = findViewById(R.id.infoButton);

        infoButton.setOnClickListener(this::infoButtonCallback);

        identifierText.setText(course.toString());
        nameText.setText(course.displayName());

        return stub;
    }

    private void infoButtonCallback(View view) {
        Intent intent = new Intent(parentActivity, CourseInfoActivity.class);
        intent.putExtra("courseId", course.id());
        intent.putExtra("headerTitle", course.toString());
        parentActivity.startActivity(intent);
    }
}
