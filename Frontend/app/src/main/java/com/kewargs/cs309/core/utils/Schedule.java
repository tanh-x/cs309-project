package com.kewargs.cs309.core.utils;

public class Schedule {
    public final int section_id;
    public final int start_time;
    public final int end_time;
    public final int meet_days_bitmask;

    public Schedule(int section_id, int start_time, int end_time, int meet_days_bitmask) {
        this.section_id = section_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.meet_days_bitmask = meet_days_bitmask;
    }
}
