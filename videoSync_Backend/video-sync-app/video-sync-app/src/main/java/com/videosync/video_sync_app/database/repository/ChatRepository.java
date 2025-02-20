package com.videosync.video_sync_app.database.repository;

import com.videosync.video_sync_app.database.entity.Chat;
import com.videosync.video_sync_app.dto.ChatDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends CrudRepository<Chat, UUID> {

    @Query("SELECT new com.videosync.video_sync_app.dto.ChatDTO(" +
            "c.id, c.sender.name, c.sender.id, c.message, c.sentAt) " +
            "FROM Chat c " +
            "WHERE c.video.id = :videoId " +
            "ORDER BY c.sentAt ASC")
    List<ChatDTO> findChatsByVideoId(@Param("videoId") UUID videoId);

    List<Chat> findByVideoIdOrderBySentAtAsc(UUID videoId);
}
