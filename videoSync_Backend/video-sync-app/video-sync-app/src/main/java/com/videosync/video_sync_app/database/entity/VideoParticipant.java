package com.videosync.video_sync_app.database.entity;

import com.videosync.video_sync_app.database.entity.enums.ParticipantStatus;
import com.videosync.video_sync_app.database.entity.enums.ParticipantType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "video_participants", uniqueConstraints = @UniqueConstraint(columnNames = {"video_id", "user_id"}))
public class VideoParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ParticipantType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipantStatus status = ParticipantStatus.PENDING;

    @Column(name = "can_control_video", nullable = false)
    private boolean canControlVideo = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    public VideoParticipant() {
    }

    public VideoParticipant(UUID id, Video video, User user, ParticipantType type,
                            ParticipantStatus status, boolean canControlVideo, LocalDateTime createdAt, LocalDateTime respondedAt) {
        this.id = id;
        this.video = video;
        this.user = user;
        this.type = type;
        this.status = status;
        this.canControlVideo = canControlVideo;
        this.createdAt = createdAt;
        this.respondedAt = respondedAt;
    }

    public VideoParticipant(Video video, User user, ParticipantType type, LocalDateTime createdAt) {
        this.video = video;
        this.user = user;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParticipantType getType() {
        return type;
    }

    public void setType(ParticipantType type) {
        this.type = type;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }

    public boolean isCanControlVideo() {
        return canControlVideo;
    }

    public void setCanControlVideo(boolean canControlVideo) {
        this.canControlVideo = canControlVideo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }
}



