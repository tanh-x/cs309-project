package cs309.backend.util.scheduling;

class Schedule {
    public final int id;
    public final int section_id;
    public final int start_time;
    public final int end_time;
    public final int meet_days_bitmask;

    public Schedule(int id, int section_id, int start_time, int end_time, int meet_days_bitmask) {
        this.id = id;
        this.section_id = section_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.meet_days_bitmask = meet_days_bitmask;
    }
}
