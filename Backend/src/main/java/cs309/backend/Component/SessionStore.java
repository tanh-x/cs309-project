package cs309.backend.Component;

import javax.websocket.Session;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SessionStore {

    // Maps to store sessions and user info
    private Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private Map<String, Session> usernameSessionMap = new Hashtable<>();

    public Map<Session, String> getSessionUsernameMap() {
        return sessionUsernameMap;
    }

    public Map<String, Session> getUsernameSessionMap() {
        return usernameSessionMap;
    }
}
