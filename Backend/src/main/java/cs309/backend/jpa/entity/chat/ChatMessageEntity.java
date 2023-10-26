package cs309.backend.jpa.entity.chat;
import lombok.Data;
import java.util.Date;




@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Lob
    private String content;

    @Column(name = "sent")
    private Date sent = new Date();

    public ChatMessageEntity() {}

    public ChatMessageEntity(String username, String content) {
        this.userName = userName;
        this.content = content;
    }
}
