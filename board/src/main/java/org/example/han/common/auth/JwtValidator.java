package org.example.han.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtValidator {

    public static Claims validateAndGetClaims(String key, String token) {

        if(token == null) return null;

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            Date exp = new Date((Long)claims.get("exp"));
            if(exp.before(new Date())) {
                throw new ExpiredJwtException(Jwts.header(), claims, "Token Expired");
            }

            return claims;
        } catch (Exception exception) {
            log.error("token parsing error!! e.message = {}", exception.getMessage());
        }
        return null;
    }

}
