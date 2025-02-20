package com.videosync.video_sync_app.security.JwtToken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChainForFormLogin(HttpSecurity http, JwtHeaderSecurityContextRepository jwtHeaderSecurityContextRepository) throws Exception {
        http
                // Disable CSRF for stateless APIs
                .csrf(csrf -> csrf.disable())

                // Use custom SecurityContextRepository
                .securityContext(context -> context
                        .securityContextRepository(jwtHeaderSecurityContextRepository)
                )

                // Configure CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Stateless session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/oauth2/code/google", "/oauth2/authorization/google").permitAll()
                        .requestMatchers("/api/**","/syncChanges").authenticated()
                            .anyRequest().permitAll()
                )

                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth2LoginConfig -> oauth2LoginConfig
                        .failureHandler((request, response, exception) ->
                            response.sendRedirect("http://localhost:5713/login")
                        ))

        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5713")); // Allow frontend origin
        config.setAllowedMethods(List.of("*")); // Allow these methods
        config.setAllowedHeaders(List.of("*")); // Allow all headers
        config.setAllowCredentials(true); // Allow credentials
        config.setMaxAge(3600L); // Cache preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config); // Apply to all /api endpoints
        return source;
    }

}
