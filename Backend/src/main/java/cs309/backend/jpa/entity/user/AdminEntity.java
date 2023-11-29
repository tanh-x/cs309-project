package cs309.backend.jpa.entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Admin")
public final class AdminEntity implements User {
    @Id
    @Column(name = "uid")
    private int uid;

    @MapsId
    @OneToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private UserEntity user;

    @Column(name = "has_access")
    private boolean hasAccess;

    public AdminEntity(int lastIdentity, boolean hasAccess) {
    }

    public AdminEntity() {

    }
}
