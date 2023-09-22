package coms309.Models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue
    private UUID ID;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String userName;
    @Setter
    private String address;

    public User(String firstName, String lastName, String email, String userName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.address = address;
    }

    public User() {

    }
}
