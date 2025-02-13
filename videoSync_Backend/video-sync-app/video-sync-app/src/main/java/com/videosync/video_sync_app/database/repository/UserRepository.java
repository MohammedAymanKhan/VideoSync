package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByGoogleId(String googleId);

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"videoParticipants"})
    Optional<User> findWithVideoParticipantsById(UUID id);

    @EntityGraph(attributePaths = {"videos"})
    Optional<User> findWithVideosById(UUID id);

    @EntityGraph(attributePaths = {"videoParticipants", "videos"})
    Optional<Video> findWithVideoParticipantsAndVideosById(UUID id);
}
