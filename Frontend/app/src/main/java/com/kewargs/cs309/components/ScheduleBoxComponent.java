package com.kewargs.cs309.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.managers.SessionManager;
import com.kewargs.cs309.core.models.in.SectionDeserializable;
import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.kewargs.cs309.core.models.in.ScheduleDeserializable;
import com.kewargs.cs309.core.utils.CourseHelper;
import com.kewargs.cs309.core.utils.Helpers;

import java.util.ArrayList;
import java.util.Objects;

public final class ScheduleBoxComponent extends InflatableComponent<ConstraintLayout> {

    private final AbstractActivity parentActivity;
    private View rootView;
    private LinearLayout parent,m,t,w,r,f;

    private final float hour_length  = 18.5F;

    private ArrayList<ArrayList<CourseHelper>> sched;

    public ScheduleBoxComponent(
            LayoutInflater inflater,
            AbstractActivity parentActivity,
            ArrayList<ArrayList<CourseHelper>> sched
    ) {
        super(R.layout.schedule_box, inflater);
        this.parentActivity = parentActivity;
        this.sched = sched;

    }
    @SuppressLint("SetTextI18n")
    @Override
    protected ConstraintLayout render() {
        super.render();

        parent  =  findViewById(R.id.parentLayout);
        m = parent.findViewById(R.id.mon);
        t = parent.findViewById(R.id.tues);
        w = parent.findViewById(R.id.wed);
        r = parent.findViewById(R.id.thurs);
        f = parent.findViewById(R.id.fri);

        return stub;

    }

    private void build()
    {//18.5dp is 1 hour
        for(CourseHelper h: sched.get(0)){
            int height = (int) Math. round((h.s.end_time-h.s.start_time-111)*0.3);
            int padding = (int) Math. round((h.s.start_time-111)*0.3);
            addNewTextView(m,h.c.program_identifier+"\n"+h.c.num,70,height,18,padding,"#CBC3E3");
        }
        for(CourseHelper h: sched.get(1)){
            int height = (int) Math. round((h.s.end_time-h.s.start_time-111)*0.3);
            int padding = (int) Math. round((h.s.start_time-111)*0.3);
            addNewTextView(t,h.c.program_identifier+"\n"+h.c.num,70,height,18,padding,"#CBC3E3");
        }
        for(CourseHelper h: sched.get(2)){
            int height = (int) Math. round((h.s.end_time-h.s.start_time-111)*0.3);
            int padding = (int) Math. round((h.s.start_time-111)*0.3);
            addNewTextView(w,h.c.program_identifier+"\n"+h.c.num,70,height,18,padding,"#CBC3E3");
        }
        for(CourseHelper h: sched.get(3)){
            int height = (int) Math. round((h.s.end_time-h.s.start_time-111)*0.3);
            int padding = (int) Math. round((h.s.start_time-111)*0.3);
            addNewTextView(r,h.c.program_identifier+"\n"+h.c.num,70,height,18,padding,"#CBC3E3");
        }
        for(CourseHelper h: sched.get(4)){
            int height = (int) Math. round((h.s.end_time-h.s.start_time-111)*0.3);
            int padding = (int) Math. round((h.s.start_time-111)*0.3);
            addNewTextView(f,h.c.program_identifier+"\n"+h.c.num,70,height,18,padding,"#CBC3E3");
        }
    }

    private void addNewTextView(LinearLayout container, String text, int width, int height, int textSize, int padding, String backgroundColor) {
        // Create a new instance of TextView
        TextView textView = new TextView(this.parentActivity);

        // Set text and other properties
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setBackgroundColor(Color.parseColor(backgroundColor));

        // Set layout parameters
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                convertDpToPx(width), // Use a method to convert dp to pixels
                convertDpToPx(height)
        );
        textView.setLayoutParams(layoutParams);

        // Set padding
        textView.setPadding(
                0, // Left padding
                convertDpToPx(padding), // Top padding
                0, // Right padding
                0  // Bottom padding
        );

        // Add the TextView to the container
        container.addView(textView);
    }

    // A method to convert dp to pixels
    private int convertDpToPx(int dp) {
        float density = parentActivity.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }



}