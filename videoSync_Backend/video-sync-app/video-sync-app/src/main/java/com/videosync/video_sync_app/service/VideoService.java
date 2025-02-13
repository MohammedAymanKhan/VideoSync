package com.videosync.video_sync_app.service;

import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.database.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }
    public Video uploadVideo(User user, MultipartFile videoFile, String title, LocalDateTime startTime, boolean isPublic) throws IOException {
        Video video = new Video();
        video.setHostUser(user);
        video.setTitle(title);
        video.setVideoData(videoFile.getBytes());
        video.setStartTime(startTime);
        video.setInviteKey(generateInviteKey());
        video.setPublic(isPublic);
        return videoRepository.save(video);
    }

    private String generateInviteKey() {
        return UUID.randomUUID().toString();
    }
}
