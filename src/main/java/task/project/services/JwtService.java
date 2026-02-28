package task.project.services;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET = "maCleSecreteTresLonguePourJWT123456";
    private final long EXPIRATION = 1000 * 60 * 60; // 1 heure

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Générer le token
    public String generateToken(String username ,Long long1) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", long1) 
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extraire le username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Vérifier si token expiré
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Vérifier validité globale
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }


    // Lire les claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
