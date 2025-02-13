package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.VideoParticipant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface VideoParticipantRepository extends CrudRepository<VideoParticipant, UUID> {

    List<VideoParticipant> findByVideoId(UUID videoId);

    List<VideoParticipant> findByUserId(UUID userId);
}
