package cs309.backend.jpa.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Student")
public final class StudentEntity implements User {
    @Id
    @Column(name = "uid")
    private int uid;

    @MapsId
    @OneToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private UserEntity user;

    @Column(name = "primary_major")
    @NotNull
    private int primaryMajor;
}
