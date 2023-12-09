package com.kewargs.cs309.core.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SchedulingTable {
    public ArrayList<Course> courseArr;
    public final int levels;
    public ArrayList<ArrayList<ArrayList<CourseHelper>>> ScheduleList;

    /**
     * Create all sheduels given an array of courses
     *
     * @param courseArr
     */
    public SchedulingTable(ArrayList<Course> courseArr) {
        this.courseArr = courseArr;
        this.levels = courseArr.size();
        this.ScheduleList = new ArrayList<ArrayList<ArrayList<CourseHelper>>>();

        dfs((ArrayList<ArrayList<CourseHelper>>) IntStream.range(0, 5).mapToObj(ArrayList<CourseHelper>::new)
                .collect(Collectors.toList()), -1);

    }

    public ArrayList<ArrayList<ArrayList<CourseHelper>>> getSchedule(){ //baby a triple!!!
        return ScheduleList;
    }




    /**
     * Backtracking algorithm to generate all possible schedules
     *
     * @param currSched
     * @param level
     */
    public void dfs(ArrayList<ArrayList<CourseHelper>> currSched, int level) {
        g: for (Schedule s : courseArr.get(level + 1).times) {

            int[] meet_days_bitmask =  IntStream.range(0, 5).map(i -> (s.meet_days_bitmask >> (4 - i)) & 1).toArray();

            for (int i = 0; i < 5; i++) // check if any schedule conflicts with the current time-table
            {

                if (meet_days_bitmask[i] != 1)
                    continue;

                for (CourseHelper s2 : currSched.get(i)) {
                    if ((s.end_time > s2.s.start_time && s.start_time < s2.s.end_time) //idk why intellij mad at me
                            || (s2.s.end_time > s.start_time && s2.s.start_time < s.end_time))
                        break g;
                }
            }

            ArrayList<ArrayList<CourseHelper>> newSched    = (ArrayList<ArrayList<CourseHelper>>) currSched.stream()
                    .map(ArrayList::new).collect(Collectors.toList()); // copies 2d array
            for (int i = 0; i < 5; i++) {
                if (meet_days_bitmask[i] == 1) {
                    newSched.get(i).add(new CourseHelper(courseArr.get(level + 1), s));
                }
            }

            if (level + 1 == levels - 1){ // all courses been added
                for(ArrayList<CourseHelper> h: newSched) {
                    h.sort(new Comparator<CourseHelper>() {
                        @Override
                        public int compare(CourseHelper lhs, CourseHelper rhs) {
                            int p1 = lhs.s.start_time;
                            int p2 = rhs.s.start_time;
                            return Integer.compare(p1, p2);
                        }
                    });
                }
                ScheduleList.add(newSched);}
            else
                dfs(newSched, level + 1);
        }
    }

    @Override
    public String toString() {
        String f = "";
        for (ArrayList<ArrayList<CourseHelper>> h : ScheduleList) {
            f += scheduleToString(h);
        }
        return f;
    }

    public String scheduleToString(ArrayList<ArrayList<CourseHelper>> sched) {
        String f = "\n";
        f += "Monday: " + weekDayToString(sched.get(0)) + "\n";
        f += "Tuesday: " + weekDayToString(sched.get(1)) + "\n";
        f += "Wednesday: " + weekDayToString(sched.get(2)) + "\n";
        f += "Thursday: " + weekDayToString(sched.get(3)) + "\n";
        f += "Friday: " + weekDayToString(sched.get(4)) + "\n";
        return f;
    }

    public String weekDayToString(ArrayList<CourseHelper> arr) {
        String f = "";
        for (CourseHelper h : arr) {
            f += h.c.program_identifier + " " + (h.c.num) + ": (" + fromMinutesToHHmm(h.s.start_time) + "-" + fromMinutesToHHmm(h.s.end_time) + ") ";
        }
        return f;
    }

    public String fromMinutesToHHmm(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, remainMinutes);
    }
}
