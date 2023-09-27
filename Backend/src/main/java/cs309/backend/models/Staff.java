package cs309.backend.models;

import cs309.backend.EntityInterfaces.IUser;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class Staff implements IUser {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime grantAccessTime;

    @OneToOne
    private Users user;

}
