package com.videosync.video_sync_app.service;

import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.database.entity.VideoParticipant;
import com.videosync.video_sync_app.database.entity.enums.ParticipantType;
import com.videosync.video_sync_app.database.repository.UserRepository;
import com.videosync.video_sync_app.database.repository.VideoParticipantRepository;
import com.videosync.video_sync_app.database.repository.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VideoParticipantService {

    private final VideoParticipantRepository videoParticipantRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;


    public VideoParticipantService(VideoParticipantRepository videoParticipantRepository, VideoRepository videoRepository, UserRepository userRepository) {
        this.videoParticipantRepository = videoParticipantRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> requestToJoinVideo(String userId, String videoId){

        Video video = videoRepository.findById(UUID.fromString(videoId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Video not found"));

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        if (videoParticipantRepository.existsByVideoAndUser(video, user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participant already exists for this video");
        }

        VideoParticipant videoParticipant = new VideoParticipant(video, user, ParticipantType.REQUEST, LocalDateTime.now());
        VideoParticipant participant = videoParticipantRepository.save(videoParticipant);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> inviteToJoinVideo(String userId, String videoId){

        Video video = videoRepository.findById(UUID.fromString(videoId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Video not found"));

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        if (videoParticipantRepository.existsByVideoAndUser(video, user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participant already exists for this video");
        }

        VideoParticipant videoParticipant = new VideoParticipant(video, user, ParticipantType.INVITE, LocalDateTime.now());
        VideoParticipant participant = videoParticipantRepository.save(videoParticipant);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> getParticipant(String videoid){
        return ResponseEntity.ofNullable(videoParticipantRepository.findByVideoId(UUID.fromString(videoid)).stream().toList());
    }
}
