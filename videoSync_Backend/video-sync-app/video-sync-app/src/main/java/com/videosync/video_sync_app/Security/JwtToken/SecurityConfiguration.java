package com.videosync.video_sync_app.Security.JwtToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChainForFormLogin(HttpSecurity http, JwtHeaderSecurityContextRepository jwtHeaderSecurityContextRepository) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sessionManagementConfig -> sessionManagementConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .securityContext(securityContextConfig -> securityContextConfig
                        .securityContextRepository(jwtHeaderSecurityContextRepository))

                .cors(cors -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    // Allow your React frontend origin.
                    configuration.setAllowedOrigins(List.of("http://localhost:5713"));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(List.of("*"));
                    // If you want to allow cookies
                    configuration.setAllowCredentials(true);
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    cors.configurationSource(source);
                })

                .authorizeHttpRequests(authorizeConfig -> authorizeConfig
                        .requestMatchers("/login/oauth2/code/google","/oauth2/authorization/google").permitAll()
                        .requestMatchers("/api").authenticated())

                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth2LoginConfig -> oauth2LoginConfig
                        .failureHandler((request, response, exception) ->
                            response.sendRedirect("http://localhost:5713/login")
                        ));

        return http.build();
    }

}
