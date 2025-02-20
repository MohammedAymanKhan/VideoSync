package com.videosync.video_sync_app.controller;

import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.entity.Video;
import com.videosync.video_sync_app.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile, @RequestParam("thumbnail") MultipartFile thumbnail,
                                         @RequestParam("title") String title, @RequestParam(value = "startTime", required = false) String startTime,
                                         @RequestParam(value = "isPublic") boolean isPublic) {
        try {

            LocalDateTime startDateTime = null;
            if (startTime != null && !startTime.isEmpty()) {
                startDateTime = LocalDateTime.parse(startTime);
            }
            Video video = videoService.uploadVideo(videoFile, thumbnail, title, startDateTime, isPublic);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading video: " + e.getMessage());
        }
    }

    @GetMapping("/publicVideos")
    public ResponseEntity<?> getPublicVideos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int limit){
        return ResponseEntity.ofNullable(videoService.getPublicVideos(page, limit));
    }

    @GetMapping("/usersFinishedVideos")
    public ResponseEntity<?> getUserFinishedVideos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int limit){
        return ResponseEntity.ofNullable(videoService.getFinishedVideos(page, limit));
    }

    @GetMapping("/usersUpcomingVideos")
    public ResponseEntity<?> getUserUpcomingVideos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int limit){
        return ResponseEntity.ofNullable(videoService.getUpcomingVideos(page, limit));
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable String videoId){
        return ResponseEntity.ofNullable(videoService.getVideoWithChats(videoId));
    }


}
