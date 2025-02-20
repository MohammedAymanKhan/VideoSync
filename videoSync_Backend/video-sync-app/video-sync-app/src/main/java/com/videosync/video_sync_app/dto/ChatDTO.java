package com.videosync.video_sync_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDTO {
    private UUID id;
    private String senderName;
    private UUID senderId;
    private String message;
    private LocalDateTime sentAt;

    public ChatDTO(UUID id, String senderName, UUID senderId, String message, LocalDateTime sentAt) {
        this.id = id;
        this.senderName = senderName;
        this.senderId = senderId;
        this.message = message;
        this.sentAt = sentAt;
    }
}
