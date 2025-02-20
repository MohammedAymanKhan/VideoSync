package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.dto.VideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {

    @Query("SELECT new com.videosync.video_sync_app.dto.VideoDTO( "+
            "v.id, v.title, v.videoData, v.inviteKey) " +
            "FROM Video v " +
            "WHERE v.id = :id")
    Optional<VideoDTO> findByVideoId(@Param("id") UUID id);

    @Query("SELECT new com.videosync.video_sync_app.dto.VideoDTO(" +
            "v.id, u.id, u.name, v.title, v.thumbnailData, v.startTime, v.isPublic, v.createdAt) " +
            "FROM Video v JOIN v.hostUser u " +
            "WHERE v.isPublic = true AND v.startTime > :currentLocalDateTime")
    Page<VideoDTO> findPublicVideosNotStarted(@Param("currentLocalDateTime") LocalDateTime currentLocalDateTime, Pageable pageable);

    @Query("SELECT new com.videosync.video_sync_app.dto.VideoDTO(" +
            "v.id, v.title, v.thumbnailData, v.startTime, v.isPublic, v.createdAt) " +
            "FROM Video v " +
            "LEFT JOIN v.videoParticipants vp " +
            "WHERE (v.hostUser.id = :id OR (vp.user.id = :id AND vp.status = 'ACCEPTED')) " +
            "AND v.startTime < :currentLocalDateTime " +
            "GROUP BY v.id " +
            "ORDER BY v.startTime ASC")
    Page<VideoDTO> findAllUserInvolvedFinishedVideos(@Param("id") UUID id,
                                                     @Param("currentLocalDateTime") LocalDateTime currentLocalDateTime,
                                                     Pageable pageable);

    @Query("SELECT new com.videosync.video_sync_app.dto.VideoDTO(" +
            "v.id, v.hostUser.id, v.hostUser.name, v.title, v.thumbnailData, v.startTime, " +
            "v.isPublic, v.createdAt, " +
            "(CASE WHEN v.hostUser.id = :id THEN true ELSE false END) AS isHost, " +
            "(CASE WHEN v.startTime <= :currentLocalDateTime THEN true ELSE false END) AS sessionStarted) " +
            "FROM Video v " +
            "LEFT JOIN v.videoParticipants vp " +
            "WHERE (v.hostUser.id = :id OR (vp.user.id = :id AND vp.status = 'ACCEPTED')) " +
            "AND (v.startTime > :currentLocalDateTime OR v.startTime <= :currentLocalDateTime) " +
            "GROUP BY v.id " +
            "ORDER BY v.startTime ASC")
    Page<VideoDTO> findAllUserInvolvedUpcomingVideos(
            @Param("id") UUID id,
            @Param("currentLocalDateTime") LocalDateTime currentLocalDateTime,
            Pageable pageable);
    @Query("SELECT v.inviteKey FROM Video v WHERE v.id = : videoId")
    String getInviteId(UUID videoId);

    @EntityGraph(attributePaths = {"videoParticipants"})
    Optional<Video> findWithParticipantsById(UUID id);

    @EntityGraph(attributePaths = {"chats"})
    Optional<Video> findWithChatsById(UUID id);

    @EntityGraph(attributePaths = {"videoParticipants", "chats"})
    Optional<Video> findWithParticipantsAndChatsById(UUID id);

    @Query("SELECT new com.videosync.video_sync_app.database.entity.Video(v.id, v.title, v.startTime, " +
            "v.inviteKey, v.isPublic, v.createdAt) " +
            "FROM Video v " +
            "WHERE v.id = :id")
    Optional<Video> findById(@Param("id") UUID id);
}
