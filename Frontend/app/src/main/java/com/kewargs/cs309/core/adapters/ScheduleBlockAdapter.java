package com.kewargs.cs309.core.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.core.models.in.ScheduleDeserializable;
import com.kewargs.cs309.core.models.in.SectionDeserializable;

import java.util.List;

public class ScheduleBlockAdapter extends BaseAdapter {
    private Context context;
    private final List<ScheduleDeserializable> schedules;

    private static final float TIME_OFFSET = 60.0f * 6 + 30;  // Starts at 6:30
    private static final float TIME_SPAN = 60.0f * 12;  // Spans 12 hours

    public ScheduleBlockAdapter(Context context, List<ScheduleDeserializable> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.component_schedule_block, parent, false);
        }
        TextView sectionName = convertView.findViewById(R.id.event_name);

        ScheduleDeserializable sch = schedules.get(position);
        sectionName.setText(sch.sectionId());
        System.out.println(sch.sectionId());

        float top = (sch.endTime() - TIME_OFFSET) / TIME_SPAN;
        float span = (sch.endTime() - sch.startTime()) / TIME_SPAN;

        System.out.println(span);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            (int) (span * parent.getHeight()),
            1
        );

        params.gravity = Gravity.BOTTOM;
        params.weight = span;
        convertView.setLayoutParams(params);

        ((LinearLayout.LayoutParams) convertView.getLayoutParams()).topMargin = (int) (parent.getHeight() * top);

        return convertView;
    }
}
