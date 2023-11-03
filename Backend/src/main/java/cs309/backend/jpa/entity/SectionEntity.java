package cs309.backend.jpa.entity;

import cs309.backend.jpa.entity.user.SectionEntityId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Schedule")
@IdClass(SectionEntityId.class)
public class SectionEntity {
    @Id
    @Column(name = "course_id")
    private int courseId;

    @Id
    @Column(name = "section")
    private String section;

    @Id
    @Column(name = "year")
    private int year;

    @Id
    @Column(name = "season")
    private int season;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "end_time")
    private Integer endTime;

    @Column(name = "location")
    private String location;

    @Column(name = "instructor")
    private String instructor;
}
