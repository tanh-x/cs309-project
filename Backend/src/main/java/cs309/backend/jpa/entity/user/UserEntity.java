package cs309.backend.jpa.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cs309.backend.common.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "User")
public final class UserEntity implements UserDetails, User {
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
    @JsonIgnore
    private String pwdBcryptHash;

    private Role mapPrivilegeLevelToRole(int privilegeLevel) {
        switch (privilegeLevel) {
            default -> { return Role.USER; }  // If unknown privilege level, give the lowest privilege
            case 2 -> { return Role.ADMIN; }
            case 3 -> { return Role.STAFF; }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = mapPrivilegeLevelToRole(privilegeLevel);
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return pwdBcryptHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//    @ManyToOne
//    @JoinColumn(
//        name = "privilege_level",
//        referencedColumnName = "privilege_level",
//        insertable = false, updatable = false
//    )
//    private PrivilegeEntity privileges;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Override
    public String toString() {
        return "<!" + username + "!>";
    }
}