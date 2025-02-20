package com.videosync.video_sync_app.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "hostUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoParticipant> videoParticipants = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "google_id", nullable = false, unique = true)
    private String googleId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "random_color", nullable = false)
    private String randomColor; // e.g., "#AABBCC"

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(UUID id, String googleId, String name, String email, String randomColor) {
        this.id = id;
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.randomColor = randomColor;
    }

    public User(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String googleId, String name, String email, String randomColor) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.randomColor = randomColor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<VideoParticipant> getVideoParticipants() {
        return videoParticipants;
    }

    public void setVideoParticipants(List<VideoParticipant> videoParticipants) {
        this.videoParticipants = videoParticipants;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRandomColor() {
        return randomColor;
    }

    public void setRandomColor(String randomColor) {
        this.randomColor = randomColor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

