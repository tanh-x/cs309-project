import cs309.backend.Component.SessionStore;
import cs309.backend.controllers.ChatSocketController;
import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.services.MessageService;
import jakarta.websocket.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatSocketControllerTest {

    @Mock
    private SessionStore sessionStore;
    @Mock
    private MessageService messageService;
    @Mock
    private Session session;

    @InjectMocks
    private ChatSocketController chatSocketController;

    @Test
    public void onOpenTest() {
        Session mockSession = mock(Session.class);
        String username = "kim";
        String messageType = "BROADCAST";
        boolean isDeleted = false;

        chatSocketController.onOpen(mockSession, username, messageType, isDeleted);

        verify(messageService, times(1)).handleOpenSession(mockSession, username, MessageEntity.MessageType.valueOf(messageType), isDeleted);
        verify(messageService, times(1)).broadcastJoinMessage(username);
    }


    @Test
    public void onMessageTest() {
        Session mockSession = mock(Session.class);
        String message = "Test message";

        chatSocketController.onMessage(mockSession, message);

        verify(messageService, times(1)).handleIncomingMessage(mockSession, message);
    }


    @Test
    public void onCloseTest() {
        String testUsername = "kim";
        when(sessionStore.getSessionUsernameMap()).thenReturn(Collections.singletonMap(session, testUsername));
        when(sessionStore.getUsernameSessionMap()).thenReturn(Collections.singletonMap(testUsername, session));

        chatSocketController.onClose(session);

        verify(sessionStore, times(1)).getSessionUsernameMap();
        verify(sessionStore, times(1)).getUsernameSessionMap();
        String commandMessage = "!help";
        chatSocketController.onMessage(session, commandMessage);
        verify(sessionStore, times(1)).removeSession(session);
        verify(messageService, times(1)).broadcast(testUsername + " disconnected");
    }


    @Test
    public void onErrorTest() {
        Throwable mockThrowable = new Throwable("Error");
        chatSocketController.onError(session, mockThrowable);

    }
    @Test
    void onMessageHandlesCommand() {
        String commandMessage = "!help";
        chatSocketController.onMessage(session, commandMessage);
    }



}

