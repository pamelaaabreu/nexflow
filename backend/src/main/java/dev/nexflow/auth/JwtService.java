package dev.nexflow.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtService(@Value("${nexflow.jwt.secret}") String secret,
                      @Value("${nexflow.jwt.access-expiration-ms}") long accessExpiration,
                      @Value("${nexflow.jwt.refresh-expiration-ms}") long refreshExpiration) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String accessToken(UserDetails user) {
        return generate(user, accessExpiration, "access");
    }

    public String refreshToken(UserDetails user) {
        return generate(user, refreshExpiration, "refresh");
    }

    private String generate(UserDetails user, long expiration, String type) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("type", type)
                .claim("authorities", user.getAuthorities().stream().map(Object::toString).toList())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extract(token, Claims::getSubject);
    }

    public String extractType(String token) {
        return extractAll(token).get("type", String.class);
    }

    public boolean isValid(String token, UserDetails user) {
        return extractUsername(token).equalsIgnoreCase(user.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return extract(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extract(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAll(token));
    }

    private Claims extractAll(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
