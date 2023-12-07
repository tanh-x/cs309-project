package cs309.backend.jpa.entity.user;

import jakarta.persistence.*;

@Entity
@Table(name = "command_responses")
public class CommandResponseEntity {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "command", nullable = false, unique = true)
    private String command;

    @Column(name = "response", nullable = false)
    private String response;

    public String getResponse() {
        return this.response;
    }


    // Getters and setters
}
