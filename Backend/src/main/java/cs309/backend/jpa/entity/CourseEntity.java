package cs309.backend.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "program_identifier")
    private String programIdentifier;

    @Column(name = "num")
    private int num;

    @Column(name = "display_name", length = 256, nullable = false)
    private String displayName;

    @Column(name = "description", length = 4096, nullable = true)
    private String description;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "is_variable_credit")
    private Boolean isVariableCredit;

    @Column(name = "spring_offered")
    private Boolean springOffered;

    @Column(name = "summer_offered")
    private Boolean summerOffered;

    @Column(name = "fall_offered")
    private Boolean fallOffered;

    @Column(name = "winter_offered")
    private Boolean winterOffered;

    @Column(name = "is_graded")
    private Boolean isGraded;
}
