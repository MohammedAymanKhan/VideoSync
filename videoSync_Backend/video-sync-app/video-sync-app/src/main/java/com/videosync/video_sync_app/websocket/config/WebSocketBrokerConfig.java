package com.videosync.video_sync_app.websocket.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketInterceptor webSocketInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Configure an embedded broker
        registry.enableSimpleBroker("/videoSync","/chatSync");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/notify");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register WebSocket endpoint
        registry.addEndpoint("/syncChanges")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("http://localhost:5713");
    }

}
