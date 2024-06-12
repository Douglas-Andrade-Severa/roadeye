package buildrun.roadeye.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class WebSocketDisconnectController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketDisconnectController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        messagingTemplate.convertAndSendToUser(event.getSessionId(), "/disconnect", "Disconnected");
    }
}
