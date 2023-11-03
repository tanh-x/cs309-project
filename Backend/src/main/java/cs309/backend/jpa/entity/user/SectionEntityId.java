package cs309.backend.jpa.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SectionEntityId implements Serializable {
    private int courseId;
    private String section;
    private int year;
    private int season;
}
