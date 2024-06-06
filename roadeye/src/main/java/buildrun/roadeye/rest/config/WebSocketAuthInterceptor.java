package buildrun.roadeye.rest.config;

import buildrun.roadeye.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final AuthenticationService jwtServiceService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("message: "+message);
        System.out.println("channel: "+channel);
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        final var command = accessor.getCommand();
        if (StompCommand.CONNECT == command || StompCommand.SUBSCRIBE == command || StompCommand.SEND == command) {
            // get token from authorization header
            String authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring("Bearer ".length());
                System.out.println("Token: "+token);
            }
        }
        return message;
    }
}
