package cs309.backend.jpa.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
@Entity
@Data
@Getter
public class RateCourseInsightsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "course_id")
    @Setter
    private int courseId;

    @Column
    @Setter
    private String summary;

    @Column
    @Setter
    private String difficulty;

    @Column(name = "recommend_prof")
    @Setter
    private String recommendProf;

    @Column
    @Setter
    private String tags;
}
