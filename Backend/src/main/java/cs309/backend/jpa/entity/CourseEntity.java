package cs309.backend.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
@Entity
@Table(name = "Course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "program_id")
    private int programId;

    @Null
    @Column(name = "program_identifier")
    private String programIdentifier;

    @Column(name = "num")
    private int num;

    @Column(name = "display_name", length = 256, nullable = false)
    private String displayName;

    // Other columns added later
}
