package cs309.backend.Component;

import javax.websocket.Session;
import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SessionStore {
// Maps to store sessions and user info
private Map<Session, String> sessionUsernameMap = new Hashtable<>();
private Map<String, Session> usernameSessionMap = new Hashtable<>();
}
