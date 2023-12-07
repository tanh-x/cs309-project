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
    private int id;


    @JoinColumn(name = "sender", referencedColumnName = "uid")
    private Integer sender;

    @JoinColumn(name = "receiver", referencedColumnName = "uid")
    private Integer receiver;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_date", nullable = false)
    private Date sentDate = new Date();

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public enum MessageType {
        DIRECT, BROADCAST
    }
}
