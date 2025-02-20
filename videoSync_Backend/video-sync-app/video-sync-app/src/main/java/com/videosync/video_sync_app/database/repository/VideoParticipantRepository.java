package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.database.entity.VideoParticipant;
import com.videosync.video_sync_app.dto.VideoParticipantDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VideoParticipantRepository extends CrudRepository<VideoParticipant, UUID> {

    @Query("SELECT new com.videosync.video_sync_app.dto.VideoParticipantDTO(" +
            "vp.id, vp.user.id, vp.user.name, vp.user.email) " +
            "FROM VideoParticipant vp " +
            "WHERE vp.video.id = :videoId")
    List<VideoParticipantDTO> findByVideoId(@Param("videoId") UUID videoId);

    List<VideoParticipant> findByUserId(UUID userId);

    boolean existsByVideoAndUser(Video video, User user);
}
