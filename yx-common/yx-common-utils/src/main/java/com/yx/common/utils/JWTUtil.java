package com.yx.common.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static final String SECRET_KEY = "@qa^oiub=~*()^55";
    private static final Long EXPIRE_TIME = 60 * 1000L;

    public static String createJwt(Map<String, Object> claims, Long expireTime) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, generalKey())
                .setSubject(String.valueOf(System.currentTimeMillis() + expireTime));
        return builder.compact();
    }

    public static Claims parseJwt(String jwt) throws SignatureException {
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody();
    }

    public static boolean isExpire(Claims claims) {
        try {
            return Long.valueOf(claims.getSubject()) <= System.currentTimeMillis();
        } catch (SignatureException e) {
            return true;
        }
    }

    public static boolean isExpire(String jwt) {
        try {
            Claims claims = parseJwt(jwt);
            return Long.valueOf(claims.getSubject()) <= System.currentTimeMillis();
        } catch (SignatureException e) {
            return true;
        }
    }

    public static SecretKey generalKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }

}
