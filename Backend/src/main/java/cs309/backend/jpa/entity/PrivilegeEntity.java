package cs309.backend.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Privilege")
public class PrivilegeEntity {
    @Id
    @Column(name = "privilege_level")
    private int privilegeLevel;

    @Column(name = "display_name", length = 256, nullable = false)
    private String displayName;
}
