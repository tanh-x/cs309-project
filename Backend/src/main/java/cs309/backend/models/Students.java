package cs309.backend.models;

import cs309.backend.EntityInterfaces.IUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;
import org.apache.catalina.User;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class Students implements IUser {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    private String primary_major;

    @OneToOne
    private Users user;

}
