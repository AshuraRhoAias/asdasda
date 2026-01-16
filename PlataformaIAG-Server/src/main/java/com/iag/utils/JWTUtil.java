package com.iag.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JWTUtil {
    
private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY") != null 
    ? System.getenv("JWT_SECRET_KEY") 
    : "PlataformaIAG-SecretKey-2024-SuperSecureKey123456789";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas
    
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    public static String generateToken(int userId, String email, String rol) {
    return Jwts.builder()
            .setSubject(email)
            .claim("userId", userId) // Guardado como Integer directamente
            .claim("rol", rol)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}
    
    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Integer getUserIdFromToken(String token) {
    Claims claims = validateToken(token);
    if (claims == null) {
        return null;
    }
    
    try {
        Object userIdObj = claims.get("userId");
        
        if (userIdObj == null) {
            return null;
        }
        
        // Si es Integer directamente (lo normal ahora)
        if (userIdObj instanceof Integer) {
            return (Integer) userIdObj;
        }
        
        // Si es otro tipo de Number
        if (userIdObj instanceof Number) {
            return ((Number) userIdObj).intValue();
        }
        
        // Fallback: intentar parsear como String (compatibilidad)
        return Integer.parseInt(userIdObj.toString());
        
    } catch (Exception e) {
        System.err.println("Error al extraer userId del token: " + e.getMessage());
        return null;
    }
}
    
    public static String getRolFromToken(String token) {
        Claims claims = validateToken(token);
        if (claims == null) return null;
        
        try {
            Object rol = claims.get("rol");
            return rol != null ? rol.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static boolean isTokenValid(String token) {
        return validateToken(token) != null;
    }
}