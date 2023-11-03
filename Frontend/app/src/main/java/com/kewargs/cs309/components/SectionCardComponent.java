package com.kewargs.cs309.components;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.models.in.ScheduleDeserializable;
import com.kewargs.cs309.core.models.in.SectionDeserializable;
import com.kewargs.cs309.core.utils.Helpers;

public final class SectionCardComponent extends InflatableComponent<ConstraintLayout> {
    private final SectionDeserializable section;
    private final AbstractActivity parentActivity;

    private SectionCardComponent(
        LayoutInflater inflater,
        AbstractActivity parentActivity,
        SectionDeserializable section
    ) {
        super(R.layout.component_section_card, inflater);
        this.parentActivity = parentActivity;
        this.section = section;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected ConstraintLayout render() {
        super.render();

        TextView sectionTitleText = findViewById(R.id.sectionTitleText);
        TextView refNumText = findViewById(R.id.refNumText);
        TableLayout scheduleTable = findViewById(R.id.scheduleTable);

        sectionTitleText.setText("Section " + section.section() +
            (section.isOnline() ? " (online)" : "")
        );

        refNumText.setText(section.refNum());

        section.schedules().stream()
            .map(this::buildScheduleRow)
            .forEach(scheduleTable::addView);

        return stub;
    }


    private TableRow buildScheduleRow(ScheduleDeserializable schedule) {
        TableRow tableRow = new TableRow(parentActivity);

        float typeWeight = 1f;
        float startTimeWeight = 1f;
        float endTimeWeight = 1f;
        float locationWeight = 1f;
        float nameWeight = 1f;

        TextView instructionTypeText = new TextView(parentActivity);
        instructionTypeText.setLayoutParams(
            new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                typeWeight
            )
        );
        instructionTypeText.setText(
            schedule.instructionType() == null ? "" :
                schedule.instructionType()
        );

        TextView startTime = new TextView(parentActivity);
        startTime.setLayoutParams(
            new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                startTimeWeight
            )
        );
        startTime.setText(schedule.startTime() == null ? "" :
            Helpers.formatMinutes(schedule.startTime())
        );

        TextView endTimeText = new TextView(parentActivity);
        endTimeText.setLayoutParams(
            new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                endTimeWeight
            )
        );
        endTimeText.setText(schedule.endTime() == null ? "" :
            Helpers.formatMinutes(schedule.endTime())
        );

        TextView locationText = new TextView(parentActivity);
        locationText.setLayoutParams(
            new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                locationWeight
            )
        );
        locationText.setText(schedule.location() == null ? "" :
            schedule.location()
        );

        TextView instructorText = new TextView(parentActivity);
        instructorText.setLayoutParams(
            new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                nameWeight
            )
        );
        instructorText.setText(schedule.instructor() == null ? "" :
            schedule.instructor()
        );

        tableRow.addView(instructorText);
        tableRow.addView(startTime);
        tableRow.addView(endTimeText);
        tableRow.addView(locationText);
        tableRow.addView(instructorText);

        return tableRow
    }
}
