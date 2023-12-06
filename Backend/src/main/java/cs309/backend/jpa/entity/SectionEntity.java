package cs309.backend.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Section")
public class SectionEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "ref_num")
    private int refNum;

    @Column(name = "section")
    private String section;

    @Column(name = "year")
    private int year;

    @Column(name = "season")
    private int season;

    @Column(name = "is_online")
    private Boolean isOnline;

    @OneToMany(
        mappedBy = "section",
        targetEntity = ScheduleEntity.class,
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER  // Important
    )
    @JsonManagedReference  // VERY important
    private List<ScheduleEntity> schedules;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;

    public SectionEntity(int ref_num, String section, int year, int season, boolean online, CourseEntity course) {
        this.refNum = ref_num;
        this.section = section;
        this.year = year;
        this.season = season;
        this.isOnline = online;
        this.course = course;
    }

    public SectionEntity() {

    }
}
