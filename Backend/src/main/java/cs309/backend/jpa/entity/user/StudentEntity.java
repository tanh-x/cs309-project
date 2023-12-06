package cs309.backend.jpa.entity.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

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
    @Nullable
    private Integer primaryMajor;

    public StudentEntity(UserEntity user, Integer primaryMajor) {
        this.primaryMajor = primaryMajor;
        this.user = user;
    }

    public StudentEntity() {

    }
}
