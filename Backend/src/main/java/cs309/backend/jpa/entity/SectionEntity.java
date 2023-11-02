package cs309.backend.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Schedule")
public class SectionEntity{
    @Id
    @Column(name = "course_id")
    private int course_id;

    @Column(name = "section")
    private int section;

    @Column(name = "year")
    private int year;

    @Column(name = "season")
    private int season;

    @Column(name = "is_online")
    private boolean is_online;

    @Column(name = "start_time")
    private int start_time;

    @Column(name = "end_time")
    private int end_time;

    @Column(name = "location")
    private String location;

    @Column(name = "instructor")
    private String instructor;
}
