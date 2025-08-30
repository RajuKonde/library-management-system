package com.example.librarymanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Broker for public topics and user-specific queues
        registry.enableSimpleBroker("/topic", "/user");
        // Prefix for messages that are handled by @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");
        // THIS IS THE NEW LINE: Enables private messaging to /user/{username}/...
        registry.setUserDestinationPrefix("/user");
    }
}