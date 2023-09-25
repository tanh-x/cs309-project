package cs309.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.rmi.server.UID;
import java.util.UUID;

@Entity
@Data
public class Users {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(unique = true)
    @NotNull
    private String username;    //Candidate Key

    @Column
    private String lastname;    //can be null for now

    @Column
    private String firstname;   //can be null for now

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(unique = true)
    private int uid;    //University ID, Candidate Key, can leave empty

    @Column
    private String privilege_level;     //type of User(i.e. Staff or Students)

    @Column
    private String pwd_bcrypt_hash;

    @Column
    private String pwd_bcrypt_salt;
}

