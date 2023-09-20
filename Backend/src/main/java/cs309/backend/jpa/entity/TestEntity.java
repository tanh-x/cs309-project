package cs309.backend.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String data;
}
