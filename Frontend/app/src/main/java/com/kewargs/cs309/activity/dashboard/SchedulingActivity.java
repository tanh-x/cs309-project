package com.kewargs.cs309.activity.dashboard;
import static android.view.KeyEvent.ACTION_DOWN;
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
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchedulingActivity extends AbstractActivity{

    private Button backDash, addCourse, addAllCourses;

    private TextView flavortext; //for saying no schedule lol
    private EditText enterCourse;

    private ArrayList<String> dataList = new ArrayList<String>();
    public SchedulingActivity() {super(R.layout.schedule_display);}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backDash.setOnClickListener(this::toDashBoardCallback);
        addCourse.setOnClickListener(this::addEachCourse);
        addAllCourses.setOnClickListener(this::addAllCourses);
        enterCourse.requestFocus();
        enterCourse.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                addEachCourse(v);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void toDashBoardCallback(View view) {
        switchToActivity(DashboardActivity.class);
    }
    private void addEachCourse(View view) {
        //Add to arraylist
        String inputText = enterCourse.getText().toString().trim();
        if(validAdd(inputText))
        {
            showToast("Course Added!", SchedulingActivity.this);
            if (dataList.isEmpty())
                flavortext.setText("Added Courses:\n\n");
            if (!inputText.isEmpty()) {
                dataList.add(inputText.toUpperCase());
                enterCourse.setText("");
                // Use Logcat to print the data list
                printDataList();
            }
            flavortext.setText(flavortext.getText()+inputText.toUpperCase()+"\n");
            enterCourse.requestFocus();
        }
        else{
            showToast("Invalid Course!", SchedulingActivity.this);
            enterCourse.setText("");}
    }

    /**
     *  Checks if input is a string followed by number using regex (cool)
     * @param input
     * @return
     */
    private boolean validAdd(String input)
    {
        String regex =  "\\b(?:\\D+\\s+){0,2}\\D+\\d+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    private void addAllCourses(View view) {
        //Send a post request of course num and names
    }
    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(SchedulingActivity.this, newActivity);
        startActivity(intent);
    }


    private void printDataList() {
        for (String item : dataList) {
            // Use Log.d() to print debug messages
            Log.d("SchedulingActivity", item);
        }
    }

    @Override
    protected void collectElements() {
        backDash = findViewById(R.id.backDashSched);
        addCourse =  findViewById(R.id.addButton);
        enterCourse = findViewById(R.id.addCourse);
        addAllCourses = findViewById(R.id.addAllCourses);
        flavortext = findViewById(R.id.flavorText);
    }
}
