package com.videosync.video_sync_app.websocket;


import java.util.UUID;

public class ChatMessage {

    private UUID id;
    private UUID videoId;
    private UUID senderId;
    private String name;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(UUID videoId, UUID senderId, String name, String message) {
        this.videoId = videoId;
        this.senderId = senderId;
        this.name = name;
        this.message = message;
    }

    public ChatMessage(UUID id, UUID videoId, UUID senderId, String name, String message) {
        this.id = id;
        this.videoId = videoId;
        this.senderId = senderId;
        this.name = name;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVideoId() {
        return videoId;
    }

    public void setVideoId(UUID videoId) {
        this.videoId = videoId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "videoId=" + videoId +
                ", senderId=" + senderId +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
