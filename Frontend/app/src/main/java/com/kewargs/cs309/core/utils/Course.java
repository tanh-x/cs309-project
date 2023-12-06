package com.kewargs.cs309.core.utils;

import java.util.ArrayList;

public class Course {
    public final int id;
    public final String program_identifier;
    public final int num;
    public final ArrayList<Schedule> times;


    public Course(int id, String program_identifier, int num, ArrayList<Schedule> times)
    {
        this.id = id;
        this.program_identifier = program_identifier;
        this.num = num;
        this.times = times;
    }



    public void add(Schedule s)
    {
        this.times.add(s);
    }
}
