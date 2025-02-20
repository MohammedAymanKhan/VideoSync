package com.videosync.video_sync_app.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Entity
@Table(name = "videos")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoParticipant> videoParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Chat> chats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_user_id", nullable = false)
    private User hostUser;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "video_data", nullable = false, columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] videoData;

    @Lob
    @Column(name = "thumbnail_data", nullable = false, columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] thumbnailData;


    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "invite_key", nullable = false, unique = true)
    private String inviteKey;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Video() {
    }

    public Video(UUID id, String title, LocalDateTime startTime, String inviteKey, boolean isPublic, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.inviteKey = inviteKey;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<VideoParticipant> getVideoParticipants() {
        return videoParticipants;
    }

    public void setVideoParticipants(List<VideoParticipant> videoParticipants) {
        this.videoParticipants = videoParticipants;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public User getHostUser() {
        return hostUser;
    }

    public void setHostUser(User hostUser) {
        this.hostUser = hostUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getVideoData() {
        return videoData;
    }

    public void setVideoData(byte[] videoData) {
        this.videoData = videoData;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getInviteKey() {
        return inviteKey;
    }

    public void setInviteKey(String inviteKey) {
        this.inviteKey = inviteKey;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getThumbnailData() {
        return thumbnailData;
    }

    public void setThumbnailData(byte[] thumbnailData) {
        this.thumbnailData = thumbnailData;
    }
}

