package cs309.backend.Component;

import java.util.Hashtable;
import java.util.Map;

import jakarta.websocket.Session;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SessionStore {
    // Maps to store sessions and user info
    private final Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private final Map<String, Session> usernameSessionMap = new Hashtable<>();
    public void removeSession(Session session) {
        String username = sessionUsernameMap.remove(session);
        if (username != null) {
            usernameSessionMap.remove(username);
        }
    }
}
