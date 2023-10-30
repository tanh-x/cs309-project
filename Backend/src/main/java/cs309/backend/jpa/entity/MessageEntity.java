package cs309.backend.jpa.entity;

import cs309.backend.jpa.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "uid")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "")
    private UserEntity receiver;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_date", nullable = false)
    private Date sentDate = new Date();

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public enum MessageType {
        DIRECT, BROADCAST
    }
}
