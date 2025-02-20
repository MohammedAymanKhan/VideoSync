package com.videosync.video_sync_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.videosync.video_sync_app.database.entity.VideoParticipant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private UUID id;
    private UUID hostUserId;
    private String hostUserName;
    private String title;
    private byte[] videoData;
    private byte[] thumbnailData;
    private String startTime;
    private boolean isPublic;
    private String createdAt;
    private boolean isHost;
    private boolean sessionStarted;
    private String inviteKey;
    private List<ChatDTO> chats;

    public VideoDTO(UUID id, UUID hostUserId, String hostUserName, String title, byte[] thumbnailData,
                    LocalDateTime startTime, boolean isPublic, LocalDateTime createdAt) {
        this.id = id;
        this.hostUserId = hostUserId;
        this.hostUserName = hostUserName;
        this.title = title;
        this.thumbnailData = thumbnailData;
        this.isPublic = isPublic;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.startTime = startTime.format(formatter);
        this.createdAt = createdAt.format(formatter);
    }

    public VideoDTO(UUID id, String title, byte[] thumbnailData,
                    LocalDateTime startTime, boolean isPublic, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.thumbnailData = thumbnailData;
        this.isPublic = isPublic;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.startTime = startTime.format(formatter);
        this.createdAt = createdAt.format(formatter);
    }

    public VideoDTO(UUID id, UUID hostUserId, String hostUserName,
                    String title, byte[] thumbnailData, LocalDateTime startTime,
                    boolean isPublic, LocalDateTime createdAt, boolean isHost, boolean sessionStarted) {
        this.id = id;
        this.hostUserId = hostUserId;
        this.hostUserName = hostUserName;
        this.title = title;
        this.thumbnailData = thumbnailData;
        this.isPublic = isPublic;
        this.isHost = isHost;
        this.sessionStarted = sessionStarted;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.startTime = startTime.format(formatter);
        this.createdAt = createdAt.format(formatter);
    }

    public VideoDTO(UUID id, String title, byte[] videoData, String inviteKey) {
        this.id = id;
        this.title = title;
        this.videoData = videoData;
        this.inviteKey = inviteKey;
    }

    public List<ChatDTO> getChats() {
        return chats;
    }

    public void setChats(List<ChatDTO> chats) {
        this.chats = chats;
    }
}

