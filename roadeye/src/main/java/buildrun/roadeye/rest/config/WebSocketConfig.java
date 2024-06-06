package buildrun.roadeye.rest.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${app.websocket.endpoint}")
    private String websocketEndpoint;

    @Value("${app.websocket.destination.prefix}")
    private String destinationPrefix;

    @Value("${app.websocket.broker.prefixes}")
    private String[] messageBrokerPrefixes;

    @Value("${app.websocket.allowed.origins}")
    private String[] allowedOrigins;

    @Bean
    public WebSocketStompClient stompClient() {
        return new WebSocketStompClient(new StandardWebSocketClient());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(websocketEndpoint).setAllowedOrigins(allowedOrigins);
        registry.addEndpoint(websocketEndpoint).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(destinationPrefix);
        config.enableSimpleBroker(messageBrokerPrefixes);
    }
}
