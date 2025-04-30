package FutureCraft.tikitaka.back_end.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("topic");
        // 웹 소켓 연결 후 채팅 전송 endPoint = @RequestMapping
        registry.setApplicationDestinationPrefixes("app");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹 소켓 연결 endPoint
        registry.addEndpoint("/socket/conn").setAllowedOriginPatterns("*").withSockJS();
    }
    
}
