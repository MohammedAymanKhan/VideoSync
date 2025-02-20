package com.videosync.video_sync_app.security.JwtToken;

import com.videosync.video_sync_app.security.AuthUser;
import com.videosync.video_sync_app.database.entity.User;
import com.videosync.video_sync_app.database.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class JwtHeaderSecurityContextRepository implements SecurityContextRepository {

    private final JwtTokenUtility jwtTokenUtility;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final UserRepository userRepository;


    public JwtHeaderSecurityContextRepository(UserRepository userRepository, JwtTokenUtility jwtTokenUtility){
        this.jwtTokenUtility = jwtTokenUtility;
        this.userRepository = userRepository;
        securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    }


    private SecurityContext readSecurityContextFromHeader(HttpServletRequest request){
        String authorizationHeader = request.getHeader("authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        if(jwt == null){
            jwt = request.getParameter("token");
        }

        if (jwt != null) {
            if (jwtTokenUtility.validateToken(jwt)) {
                User user = jwtTokenUtility.getUser(jwt);
                Authentication authentication = new AuthUser(user);
                SecurityContext securityContext = this.generateNewContext();
                securityContext.setAuthentication(authentication);
                return securityContext;
            }
        }

        return null;
    }

    private SecurityContext generateNewContext() {
        return this.securityContextHolderStrategy.createEmptyContext();
    }

    private String generateRandomColor() {
        Random random = new Random();
        int nextInt = random.nextInt(0xFFFFFF + 1);
        return String.format("#%06X", nextInt);
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        SecurityContext context = this.readSecurityContextFromHeader(request);
        if (context == null) {
            context = this.generateNewContext();
        }

        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        if (context.getAuthentication() instanceof OAuth2AuthenticationToken oauthToken) {

            OAuth2User oauthUser = oauthToken.getPrincipal();
            String email = oauthUser.getAttribute("email");
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user;
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                user = new User(oauthUser.getAttribute("sub"),
                        oauthUser.getAttribute("name"),
                        oauthUser.getAttribute("email"),
                        generateRandomColor());
                user = userRepository.save(user);
            }

            String jwtToken = jwtTokenUtility.generateToken(user);
            response.addHeader("authorization", "Bearer " + jwtToken);
            try {
                response.sendRedirect("http://localhost:5713/login/oauth2/token/google?token="+jwtToken);
            } catch (IOException e) {
                System.out.println("***Redirect Error***");
            }
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return this.readSecurityContextFromHeader(request) != null;
    }
}
