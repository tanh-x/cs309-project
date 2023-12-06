package com.kewargs.cs309.activity.dashboard;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.ACTION_UP;
import static android.view.KeyEvent.KEYCODE_ENTER;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kewargs.cs309.MainActivity;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.managers.SessionManager;
import com.kewargs.cs309.core.models.in.CourseDeserializable;
import com.kewargs.cs309.core.models.in.ScheduleDeserializable;
import com.kewargs.cs309.core.models.in.SectionDeserializable;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.Course;
import com.kewargs.cs309.core.utils.Schedule;
import com.kewargs.cs309.core.utils.backend.factory.CourseRequestFactory;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchedulingActivity extends AbstractActivity{

    private Button backDash, deleteAllCourses, addAllCourses;

    private ArrayList<SectionDeserializable> sections;

    private CourseDeserializable tempC = null;

    private String CourseName = "";

    private int CourseNum = 0;



    private TextView flavortext; //for saying no schedule lol

    private ArrayList<String> dataList = new ArrayList<String>();
    public SchedulingActivity() {super(R.layout.schedule_display);}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseList();
        session.courseArrList.clear();
        backDash.setOnClickListener(this::toDashBoardCallback);
        deleteAllCourses.setOnClickListener(this::deleteAllCourses);
        addAllCourses.setOnClickListener(this::addAllCourses);
    }

    private void courseList(){
        String f = "Course ID:\n\n";
        SessionManager session = SessionManager.getInstance();
        for(int i: session.courseArr){f+=i+"\n";}
        flavortext.setText(f);
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

        for(int i: session.courseArr)
        {
            getSchedulefromCourse(i);
        }
        printDataList(session.courseArrList);

        showToast("All courses added",this);
    }

    private void deleteAllCourses(View view) {
        //Send a post request of course num and names
        SessionManager.getInstance().courseArr.clear();
        showToast("All courses removed",this);
        courseList();
    }
    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(SchedulingActivity.this, newActivity);
        startActivity(intent);
    }
    public void courseInfo(int cid){
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
                            Log.d("Sections",section.toString());
                            for (ScheduleDeserializable schedule : section.schedules()) {
                                if (schedule.startTime() != null
                                        && schedule.endTime() != null
                                        && schedule.endTime() > schedule.startTime()
                                        && schedule.meetDaysBitmask()!=null
                                ) {
                                    Log.d("Passed filter",schedule.toString());
                                    Schedule s = new Schedule(schedule.sectionId(),schedule.startTime(),schedule.endTime(),schedule.meetDaysBitmask());
                                    Log.d("properties",s.toString());
                                    if(isInteger(section.section()))
                                        lecture.add(s);
                                    else
                                        recitation.add(s);
                                    break;
                                }
                            }
                        }
                        if(!lecture.isEmpty())
                        {
                            Course cour = new Course(cid,CourseName,CourseNum,lecture);
                            Log.d("course info",cour.toString());
                            session.courseArrList.add(cour);
                        }
                        if(!recitation.isEmpty())
                        {
                            Course cour = new Course(cid,CourseName,CourseNum,recitation);
                            Log.d("course info",cour.toString());
                            session.courseArrList.add(cour);
                        }
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
        String f ="";
        if( cArr.isEmpty())
        {
            f = "No courses added";
        }
        for (Course co: cArr) {
            // Use Log.d() to print debug messages
            for(Schedule s:co.times){
                f+=s.start_time + " " + s.end_time + "\n";
            }
            Log.d("SchedulingActivity", co.program_identifier + " " + co.num + ":\n");
        }
        flavortext.setText(f);
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
        deleteAllCourses =  findViewById(R.id.removeAllCourses);
        addAllCourses = findViewById(R.id.addAllCourses);
        flavortext = findViewById(R.id.flavorText);
    }
}
