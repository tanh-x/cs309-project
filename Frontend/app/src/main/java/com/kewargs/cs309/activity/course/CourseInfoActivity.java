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

import com.google.android.material.chip.ChipGroup;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.components.SectionCardComponent;
import com.kewargs.cs309.core.adapters.ScheduleBlockAdapter;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.models.in.InsightsDeserializable;
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
    private ArrayList<InsightsDeserializable> insights = null;

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
    private ChipGroup tagsChipGroup;
    private TextView difficultyText;
    private TextView profText;

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

        session.addRequest(CourseRequestFactory.getCourseInsights(courseId)
            .onResponse(response -> {
                try {
                    insights = InsightsDeserializable.fromArray(new JSONArray(response));
                    buildInsightsComponents();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            })
            .onError(error -> {
                showToast("Couldn't get course insights.", this);
                finish();
            })
            .build()
        );
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
        if (insights.isEmpty()) return;
        InsightsDeserializable ins = insights.get(0);

        String summary = ins.summary();
        insightsText.setText((summary == null || Objects.equals(summary, "null")) ?
            "No insight summary found for this course." :
            ins.summary()
        );

        String prof = ins.recommendProf();
        profText.setText((prof == null || Objects.equals(prof, "null")) ?
            "∙ No professor recommendations found for this course." :
            ins.recommendProf()
        );

        if (ins.difficulty() == null) {
            difficultyText.setText("∙ No difficulty ratings found for this course");
        } else {
            double difficulty = ins.difficulty();
            double artanhDifficulty = 5 * Math.log((1.0 + difficulty) / (1.0 - difficulty));
            artanhDifficulty = Math.round(artanhDifficulty * 100.0d) / 100.0d;
            String difficultyComment;
            if (difficulty < -0.75) difficultyComment = "significantly easier than average";
            else if (difficulty < -0.4) difficultyComment = "easier than average";
            else if (difficulty < -0.1) difficultyComment = "slightly easier than average";
            else if (difficulty < 0.2) difficultyComment = "average in difficulty";
            else if (difficulty < 0.4) difficultyComment = "slightly harder than average";
            else if (difficulty < 0.8) difficultyComment = "harder than average";
            else if (difficulty < 1.0) difficultyComment = "significantly harder than average";
            else difficultyComment = null;

            difficultyText.setText(
                "∙ The difficulty was rated by students at "
                    + (difficulty > 0 ? "+" : "") + artanhDifficulty +
                    " (" + (difficulty > 0 ? "+" : "") + Math.round(difficulty * 10000.0d) / 100.0d + "%)" +
                    (difficultyComment == null ? "." : ",\n which means that it's " + difficultyComment + "."));
        }
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
        tagsChipGroup = findViewById(R.id.tagsChipGroup);
        difficultyText = findViewById(R.id.difficultyText);
        profText = findViewById(R.id.profText);
    }
}
