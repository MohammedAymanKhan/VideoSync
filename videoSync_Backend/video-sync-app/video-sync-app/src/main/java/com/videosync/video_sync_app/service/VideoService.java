package com.videosync.video_sync_app.service;

import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.database.repository.ChatRepository;
import com.videosync.video_sync_app.database.repository.VideoRepository;
import com.videosync.video_sync_app.dto.ChatDTO;
import com.videosync.video_sync_app.dto.VideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final ChatRepository chatRepository;

    public VideoService(VideoRepository videoRepository, ChatRepository chatRepository){
        this.videoRepository = videoRepository;
        this.chatRepository = chatRepository;
    }

    public Video uploadVideo(MultipartFile videoFile, MultipartFile thumbnail, String title,
                             LocalDateTime startTime, boolean isPublic) throws IOException {
        User user = getAuthenticatedUser();
        Video video = new Video();
        video.setHostUser(user);
        video.setTitle(title);
        video.setVideoData(videoFile.getBytes());
        video.setThumbnailData(thumbnail.getBytes());
        video.setStartTime(startTime);
        video.setInviteKey(generateInviteKey());
        video.setPublic(isPublic);
        return videoRepository.save(video);
    }

    public List<VideoDTO> getPublicVideos(int page, int limit){
       Page<VideoDTO> videoDTOS =  videoRepository.findPublicVideosNotStarted(LocalDateTime.now(), PageRequest.of(page, limit));
       return  videoDTOS.stream().toList();
    }

    public List<VideoDTO> getFinishedVideos(int page, int limit){
        User user = getAuthenticatedUser();
        return videoRepository.findAllUserInvolvedFinishedVideos(user.getId(),LocalDateTime.now(), PageRequest.of(page, limit)).toList();
    }

    public List<VideoDTO> getUpcomingVideos(int page, int limit){
        User user = getAuthenticatedUser();
        return videoRepository.findAllUserInvolvedUpcomingVideos(user.getId(),LocalDateTime.now(), PageRequest.of(page, limit)).toList();
    }

    public VideoDTO getVideo(String videoId){
        return videoRepository.findByVideoId(UUID.fromString(videoId)).orElse(null);
    }
    public Optional<VideoDTO> getVideoWithChats(String videoId) {
        Optional<VideoDTO> videoDTO = videoRepository.findByVideoId(UUID.fromString(videoId));
        if (videoDTO.isPresent()) {
            List<ChatDTO> chats = chatRepository.findChatsByVideoId(UUID.fromString(videoId));
            videoDTO.get().setChats(chats);
        }
        return videoDTO;
    }

    private String generateInviteKey() {
        return UUID.randomUUID().toString();
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
