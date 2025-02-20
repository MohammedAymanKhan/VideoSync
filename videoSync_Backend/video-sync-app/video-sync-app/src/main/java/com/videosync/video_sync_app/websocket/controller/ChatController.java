package com.videosync.video_sync_app.websocket.controller;

import com.videosync.video_sync_app.database.entity.Chat;
import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.database.repository.ChatRepository;
import com.videosync.video_sync_app.database.repository.UserRepository;
import com.videosync.video_sync_app.database.repository.VideoRepository;
import com.videosync.video_sync_app.websocket.ChatMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatRepository chatRepository, VideoRepository videoRepository, UserRepository userRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatRepository = chatRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    @Transactional  // Add this annotation
    public void storeChats(@Payload ChatMessage message, @Header("chatRoomId") String chatRoomId) {
        User user = userRepository.findById(message.getSenderId()).orElse(null);
        Video video = videoRepository.findById(message.getVideoId()).orElse(null);

        if (user != null && video != null) {
            Chat chat = chatRepository.save(new Chat(video, user, message.getMessage(), LocalDateTime.now()));
            simpMessagingTemplate.convertAndSend("/chatSync/" + chatRoomId, chat);
        } else {
            System.out.println("No Video or such user exists");
        }
    }





}
