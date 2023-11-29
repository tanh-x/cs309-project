package cs309.backend.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Schedule")
public class ScheduleEntity {
    @Id
    @Column(name = "id")
    private int id;

//    @Column(name = "section_id")
//    private int sectionId;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "end_time")
    private Integer endTime;

    @Column(name = "meet_days_bitmask")
    private Integer meetDaysBitmask;

    @Column(name = "location")
    private String location;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "instruction_type")
    private String instructionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @JsonIgnore
    private SectionEntity section;

    @JsonProperty("sectionId")
    public int getSectionId() {
        return section.getId();
    }


}
