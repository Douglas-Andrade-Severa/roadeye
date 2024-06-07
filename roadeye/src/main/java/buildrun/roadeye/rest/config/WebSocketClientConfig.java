package buildrun.roadeye.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketClientConfig {
    @Bean
    public WebSocketStompClient stompClient() {
        return new WebSocketStompClient(new StandardWebSocketClient());
    }
}
