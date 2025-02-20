package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.Chat;
import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.dto.UserInviteDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT new com.videosync.video_sync_app.dto.UserInviteDTO(u.id, u.name, u.email) " +
            "FROM User u " +
            "WHERE lower(u.email) LIKE lower(concat('%', :query, '%')) " +
            "   OR lower(u.name) LIKE lower(concat('%', :query, '%'))")
    List<UserInviteDTO> searchByEmailOrName(@Param("query") String query);

    @Query("SELECT u FROM User u WHERE u.googleId = :googleId")
    Optional<User> findByGoogleId(@Param("googleId") String googleId);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT new com.videosync.video_sync_app.database.entity.User(u.id, u.name) FROM User u WHERE u.id = :id")
    Optional<User> findById(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"videoParticipants"})
    Optional<User> findWithVideoParticipantsById(UUID id);

    @EntityGraph(attributePaths = {"videos"})
    Optional<User> findWithVideosById(UUID id);

    @EntityGraph(attributePaths = {"videoParticipants", "videos"})
    Optional<Video> findWithVideoParticipantsAndVideosById(UUID id);

}
