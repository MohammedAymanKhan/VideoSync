package com.videosync.video_sync_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoParticipantDTO {

    private UUID id;
    private UUID userId;
    private String name;
    private String email;

}
