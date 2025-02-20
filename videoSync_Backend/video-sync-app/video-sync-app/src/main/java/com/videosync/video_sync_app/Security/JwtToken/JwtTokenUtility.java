package com.videosync.video_sync_app.security.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import com.videosync.video_sync_app.database.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenUtility {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final JwtParser jwtParser = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build();

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setSubject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("name", user.getName())
                .claim("randomColor", user.getRandomColor())
                .claim("googleId", user.getGoogleId())
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String jwtToken) {
        return extractClaims(jwtToken).getSubject();
    }

    public boolean validateToken(String jwtToken) {
        try {
            jwtParser.parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUser(String jwtToken){
        Claims claims = extractClaims(jwtToken);
        return new User(
                UUID.fromString(claims.getSubject()),
                claims.get("googleId",String.class),
                claims.get("name",String.class),
                claims.get("email",String.class),
                claims.get("randomColor",String.class));

    }

    private Claims extractClaims(String jwtToken) {
        return jwtParser
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
