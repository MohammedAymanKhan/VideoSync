package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends CrudRepository<Chat, UUID> {

    List<Chat> findByVideoIdOrderBySentAtAsc(UUID videoId);
}
