package com.ver.ssms.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    /// IMPORTANT ! THIS IS NOT RECCOMENDED IN PRODUCTION
    /// SO TO MY FUTURE SELF QUICKLY MOVE THIS TO AN ENV FILE !!!
    private final String SECRET_KEY = "U2JIhQn505/RUvC5hLdeu4QrU8SUe2H8e8gH7mFt19Y";

    public String generateToken(String username){
        return Jwts.builder().subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
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
