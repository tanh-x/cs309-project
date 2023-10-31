package com.kewargs.cs309.components;

import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kewargs.cs309.R;
import com.kewargs.cs309.core.models.in.CourseDeserializable;

public final class CourseCardComponent extends InflatableComponent<ConstraintLayout> {
    private CourseDeserializable course;

    private TextView identifierText;
    private TextView nameText;

    public CourseCardComponent(LayoutInflater inflater, CourseDeserializable course) {
        super(R.layout.component_course_list, inflater);
        this.course = course;
    }

    @Override
    protected ConstraintLayout render() {
        super.render();
        identifierText = findViewById(R.id.courseIdentifierText);
        nameText = findViewById(R.id.courseNameText);

        identifierText.setText(course.programIdentifier() + " " + course.num());
        nameText.setText(course.displayName());

        return stub;
    }
}
