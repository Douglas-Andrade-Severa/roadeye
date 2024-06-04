package buildrun.roadeye.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");//Canal
        config.setApplicationDestinationPrefixes("/app");//Mapear o ambiente
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect");
        registry.addEndpoint("/connect").withSockJS();
    }
}
