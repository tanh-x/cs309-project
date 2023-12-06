package com.kewargs.cs309.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.managers.SessionManager;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.models.in.ScheduleDeserializable;
import com.kewargs.cs309.core.models.in.SectionDeserializable;
import com.kewargs.cs309.core.utils.Course;
import com.kewargs.cs309.core.utils.Schedule;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SchedulingActivity extends AbstractActivity {

    private Button backDash, deleteAllCourses, addAllCourses;

    private ArrayList<SectionDeserializable> sections;

    private CourseDeserializable tempC = null;

    private String CourseName = "";

    private int CourseNum = 0;


    private TextView flavourText; //for saying no schedule lol

    private ArrayList<String> dataList = new ArrayList<>();

    public SchedulingActivity() { super(R.layout.schedule_display); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseList();
        SessionManager.courseArrList.clear();
        backDash.setOnClickListener(this::toDashBoardCallback);
        deleteAllCourses.setOnClickListener(this::deleteAllCourses);
        addAllCourses.setOnClickListener(this::addAllCourses);
    }

    private void courseList() {
        StringBuilder f = new StringBuilder("Course ID:\n\n");
        SessionManager session = SessionManager.getInstance();
        for (int i : SessionManager.courseArr) { f.append(i).append("\n"); }
        flavourText.setText(f.toString());
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void toDashBoardCallback(View view) {
        switchToActivity(DashboardActivity.class);
    }

    private void addAllCourses(View view) {
        //Send a post request of course num and names

        for (int i : SessionManager.courseArr) {
            getSchedulefromCourse(i);
        }
        printDataList(SessionManager.courseArrList);

        showToast("All courses added", this);
    }

    private void deleteAllCourses(View view) {
        //Send a post request of course num and names
        SessionManager.courseArr.clear();
        showToast("All courses removed", this);
        courseList();
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(SchedulingActivity.this, newActivity);
        startActivity(intent);
    }

    public void courseInfo(int cid) {
        session.addRequest(CourseRequestFactory.getCourseInfo(cid)
            .onResponse(response -> {
                tempC = CourseDeserializable.from(response);
                CourseName = tempC.programIdentifier();
                CourseNum = tempC.num();
            })
            .onError(error -> {
                showToast("Couldn't get course info.", this);
                finish();
            })
            .build());
    }

    public void getSchedulefromCourse(int cid) {
        session.addRequest(CourseRequestFactory.getCourseSections(cid)
            .onResponse(response -> {
                try {
                    courseInfo(cid);
                    sections = SectionDeserializable.fromArray(new JSONArray(response));


                    ArrayList<Schedule> lecture = new ArrayList<>();
                    ArrayList<Schedule> recitation = new ArrayList<>();
                    for (SectionDeserializable section : sections) {
                        // Populate scheduled sections
                        Log.d("Sections", section.toString());
                        for (ScheduleDeserializable schedule : section.schedules()) {
                            if (schedule.startTime() != null
                                && schedule.endTime() != null
                                && schedule.endTime() > schedule.startTime()
                                && schedule.meetDaysBitmask() != null
                            ) {
                                Log.d("Passed filter", schedule.toString());
                                Schedule s = new Schedule(schedule.sectionId(), schedule.startTime(), schedule.endTime(), schedule.meetDaysBitmask());
                                Log.d("properties", s.toString());
                                if (isInteger(section.section()))
                                    lecture.add(s);
                                else
                                    recitation.add(s);
                                break;
                            }
                        }
                    }
                    if (!lecture.isEmpty()) {
                        Course cour = new Course(cid, CourseName, CourseNum, lecture);
                        Log.d("course info", cour.toString());
                        SessionManager.courseArrList.add(cour);
                    }
                    if (!recitation.isEmpty()) {
                        Course cour = new Course(cid, CourseName, CourseNum, recitation);
                        Log.d("course info", cour.toString());
                        SessionManager.courseArrList.add(cour);
                    }
                    printDataList(SessionManager.courseArrList);
                    //return new ArrayList<>() {{add(lecture);add(recitation);}};
                } catch (JSONException e) {
                    Log.d("Mistake here", "exception");
                    throw new RuntimeException(e);
                }
            })
            .onError(error -> {
                showToast("Couldn't get schedule info.", this);
                finish();
            }).build());


    }

    private void printDataList(ArrayList<Course> cArr) {
        StringBuilder f = new StringBuilder();
        if (cArr.isEmpty()) {
            f = new StringBuilder("No courses added");
        }
        for (Course co : cArr) {
            for (Schedule s : co.times) {
                f.append(s.start_time).append(" ").append(s.end_time).append("\n");
            }
            Log.d("SchedulingActivity", co.program_identifier + " " + co.num + ":\n");
        }
        flavourText.setText(f.toString());
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void collectElements() {
        backDash = findViewById(R.id.backDashSched);
        deleteAllCourses = findViewById(R.id.removeAllCourses);
        addAllCourses = findViewById(R.id.addAllCourses);
        flavourText = findViewById(R.id.flavorText);
    }
}
