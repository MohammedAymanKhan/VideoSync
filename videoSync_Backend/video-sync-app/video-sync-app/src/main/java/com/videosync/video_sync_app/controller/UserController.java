package com.videosync.video_sync_app.controller;


import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myDetails")
    public ResponseEntity<User> getMyDetails(){
        return  ResponseEntity.ofNullable(getAuthenticatedUser());
    }

    @GetMapping("/usersSearch")
    public ResponseEntity<?> searchUsers(@RequestParam("query") String query){
        return ResponseEntity.ofNullable(userService.searchUsers(query));
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

}
