package cs309.backend.jpa.entity.user;

import cs309.backend.jpa.entity.PrivilegeEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User")
public final class  UserEntity implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int uid;

    @Column(name = "username", length = 64, nullable = false)
    private String username;

    @Column(name = "email", length = 512, nullable = false)
    private String email;

    @Column(name = "display_name", length = 256, nullable = false)
    private String displayName;

    @Column(name = "privilege_level", nullable = false)
    private int privilegeLevel;

    @Column(name = "pwd_bcrypt_hash", length = 256, nullable = false)
    private String pwdBcryptHash;

    @ManyToOne
    @JoinColumn(
        name = "privilege_level",
        referencedColumnName = "privilege_level",
        insertable = false, updatable = false
    )
    private PrivilegeEntity privileges;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;
}