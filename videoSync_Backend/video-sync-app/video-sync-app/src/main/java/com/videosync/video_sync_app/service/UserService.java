package com.videosync.video_sync_app.service;

import com.videosync.video_sync_app.database.repository.UserRepository;
import com.videosync.video_sync_app.dto.UserInviteDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserInviteDTO> searchUsers(String query) {
        return userRepository.searchByEmailOrName(query);
    }
}
