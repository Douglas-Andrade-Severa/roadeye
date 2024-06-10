package buildrun.roadeye.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    @MessageMapping("/notify")
    public void notifyClients(String message) {
        logger.info("Received WebSocket message: ", message);
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
