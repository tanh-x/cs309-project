package cs309.backend.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class Admin {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private UUID id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime grantAccessTime;

    @OneToOne
    private Users user;

}
