package cs309.backend.jpa.entity.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "CourseInsights")
public class CourseInsightsEntity {
    @Id
    @Column
    private int id;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "summary")
    private String summary;

    @Column(name = "difficulty")
    private Float difficulty;

    @Column(name = "recommend_prof")
    private String recommendProf;

    @Column(name = "tags")
    private String tags;
}
