package com.videosync.video_sync_app.controller;

import com.videosync.video_sync_app.service.VideoParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participate")
public class VideoParticipantController {

    private final VideoParticipantService videoParticipantService;

    public VideoParticipantController(VideoParticipantService videoParticipantService) {
        this.videoParticipantService = videoParticipantService;
    }

    @PostMapping("/requestJoin")
    public ResponseEntity<?> requestToJoin(@RequestParam("userId") String userId,
                                           @RequestParam("videoId") String videoId){
        return videoParticipantService.requestToJoinVideo(userId, videoId);
    }

    @PostMapping("/inviteJoin")
    public ResponseEntity<?> inviteToJoin(@RequestParam("userId") String userId,
                                           @RequestParam("videoId") String videoId){
        return videoParticipantService.inviteToJoinVideo(userId, videoId);
    }

    @GetMapping("/videoParticipants/{videoId}")
    public ResponseEntity<?> getVideoParticipant(@PathVariable("videoId") String videoId){
        return videoParticipantService.getParticipant(videoId);
    }

}
