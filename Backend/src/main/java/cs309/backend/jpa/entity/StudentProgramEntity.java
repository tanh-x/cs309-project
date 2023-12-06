package cs309.backend.jpa.entity;

import cs309.backend.common.ProgramType;
import cs309.backend.jpa.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "StudentProgram")
public class StudentProgramEntity {
    @Id
    @Column(name = "uid")
    private int uid;

    @Column(name = "program_identifier")
    private String programIdentifier;

    @Column(name = "type")
    private ProgramType type;

    @Column(name = "path")
    private String path;

    @MapsId
    @OneToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private UserEntity user;
}
