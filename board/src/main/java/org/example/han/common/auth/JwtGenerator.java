package org.example.han.common.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtGenerator {

    private static final List<String> roleNameList = List.of("USER");

    public static String generateToken(
            Long userId
            , String loginId
            , String userName
            , JwtConfigProperty jwtProperty) {

        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtProperty.getExpiredTime());

        Map<String, Object> claims = new HashMap<>();
        claims.put("info", new TokenInfo(userId, loginId, userName, roleNameList));
        claims.put("issuer", jwtProperty.getIssuer());
        claims.put("iat", iat);
        claims.put("exp", exp);

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
                .compact();
    }

}
