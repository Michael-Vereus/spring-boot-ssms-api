package com.ver.ssms.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    /// IMPORTANT ! THIS IS NOT RECCOMENDED IN PRODUCTION
    /// SO TO MY FUTURE SELF QUICKLY MOVE THIS TO AN ENV FILE !!!
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(String username){
        return Jwts.builder().subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 90))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                .compact();
    }
    /// take the username out of the token itself.
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    /// validate token from incoming request
    public boolean validateToken(String token, String username){
        final  String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !tokenExpired(token);
    }

    /// check token expires or not
    public boolean tokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    /// get claims used to decode token
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
