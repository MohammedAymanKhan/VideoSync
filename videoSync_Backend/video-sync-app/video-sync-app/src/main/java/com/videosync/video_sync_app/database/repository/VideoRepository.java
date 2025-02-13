package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.Video;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {

    Optional<Video> findById(UUID id);

    @EntityGraph(attributePaths = {"videoParticipants"})
    Optional<Video> findWithParticipantsById(UUID id);

    @EntityGraph(attributePaths = {"chats"})
    Optional<Video> findWithChatsById(UUID id);

    @EntityGraph(attributePaths = {"videoParticipants", "chats"})
    Optional<Video> findWithParticipantsAndChatsById(UUID id);
}
