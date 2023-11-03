package com.kewargs.cs309.activity.course;

import static com.kewargs.cs309.core.utils.Helpers.boolToYesNo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.components.SectionCardComponent;
import com.kewargs.cs309.core.adapters.ScheduleBlockAdapter;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.models.in.ScheduleDeserializable;
import com.kewargs.cs309.core.models.in.SectionDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * Must pass in a courseId
 */
@SuppressLint("SetTextI18n")
public final class CourseInfoActivity extends AbstractActivity {
    public CourseInfoActivity() { super(R.layout.activity_course_info); }

    private ImageButton backButton;
    private TextView headerTitle;

    private Integer courseId = null;
    private CourseDeserializable course = null;
    private ArrayList<SectionDeserializable> sections = null;

    private TextView titleText;
    private TextView descriptionText;
    private TextView creditsText;
    private TextView variableCreditsText;
    private TextView deliveryText;
    private TextView gradedText;
    private TextView seasonsText;

    private TextView sectionCountText;
    private GridView mainGrid;
    private GridView sideGrid;

    private LinearLayout sectionList;

    private TextView insightsText;

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
                    sections = SectionDeserializable.fromArray(new JSONArray(response));
                    buildScheduleComponents();
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

        buildInsightsComponents();
    }

    private void buildCourseInfoComponents() {
        if (course == null) return;

        titleText.setText(course + ": " + course.displayName());
        descriptionText.setText(course.description());
        creditsText.setText("" + course.credits());
        variableCreditsText.setText(boolToYesNo(course.isVariableCredit()));
        gradedText.setText(boolToYesNo(course.isGraded()));
        deliveryText.setText("In-person");

        seasonsText.setText(String.join(" · ", new ArrayList<>() {{
            if (Boolean.TRUE.equals(course.springOffered())) add("Spring");
            else if (course.springOffered() == null) add("Spring?");
            if (Boolean.TRUE.equals(course.summerOffered())) add("Summer");
            if (Boolean.TRUE.equals(course.fallOffered())) add("Fall");
            else if (course.fallOffered() == null) add("Fall?");
            if (Boolean.TRUE.equals(course.winterOffered())) add("Winter");
        }}));
    }

    private void buildScheduleComponents() {
        if (sections == null) return;
        sectionList.removeAllViews();

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinkedHashSet<SectionDeserializable> scheduledSections = new LinkedHashSet<>();
        LinkedHashSet<SectionDeserializable> onlineSections = new LinkedHashSet<>();
        LinkedHashSet<SectionDeserializable> offlineSections = new LinkedHashSet<>();
        LinkedHashMap<Integer, ArrayList<ScheduleDeserializable>> sectionTimes = new LinkedHashMap<>();
        for (SectionDeserializable section : sections) {
            // Populate scheduled sections
            for (ScheduleDeserializable schedule : section.schedules()) {
                if (schedule.startTime() != null
                    && schedule.endTime() != null
                    && schedule.endTime() > schedule.startTime()
                ) {
                    scheduledSections.add(section);
                    sectionTimes.putIfAbsent(section.id(), new ArrayList<>());
                    sectionTimes.get(section.id()).add(schedule);
                }
            }

            // Populate online sections
            if (Boolean.TRUE.equals(section.isOnline())) onlineSections.add(section);
            else if (Boolean.FALSE.equals(section.isOnline())) offlineSections.add(section);

            // Regardless, we still populate the tables
            SectionCardComponent sectionCard = new SectionCardComponent(layoutInflater, this, section);
            sectionCard.bindTo(sectionList);
        }
        int offlineCount = offlineSections.size();
        int onlineCount = onlineSections.size();

        if (offlineCount > 0) {
            deliveryText.setText(onlineCount > 0 ? "Both" : "In-person");
        } else {
            deliveryText.setText(onlineCount > 0 ? "Online" : "Unknown");
        }

        sectionCountText.setText(sections.size() + " sections" +
            ((onlineCount > 0) ? " (" + onlineCount + " online)" : "")
        );

        // Build the schedule
//        ScheduleBlockAdapter adapter = new ScheduleBlockAdapter(this, scheduledSections);
//        mainGrid.setAdapter(adapter);
    }

    private void buildInsightsComponents() {
        insightsText.setText("No insights found for this course.");
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

        sectionCountText = findViewById(R.id.sectionCountText);
        mainGrid = findViewById(R.id.mainGrid);
        sideGrid = findViewById(R.id.sideGrid);

        sectionList = findViewById(R.id.sectionList);

        insightsText = findViewById(R.id.insightsSummary);
    }
}
