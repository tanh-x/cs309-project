package cs309.backend.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Getter
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    @Setter
    private String name;

    @Column
    @Setter
    private String data;
}
