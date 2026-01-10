package com.iag.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JWTUtil {
    
    private static final String SECRET_KEY = "PlataformaIAG-SecretKey-2024-SuperSecureKey123456789";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas
    
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    public static String generateToken(int userId, String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", String.valueOf(userId)) // Guardar como String para evitar problemas
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
        if (claims == null) return null;
        
        try {
            // Obtener como Object y convertir según el tipo
            Object userIdObj = claims.get("userId");
            
            if (userIdObj == null) {
                return null;
            }
            
            // Si es String, parsear a Integer
            if (userIdObj instanceof String) {
                return Integer.parseInt((String) userIdObj);
            }
            
            // Si es Number (Integer, Long, Double, etc.)
            if (userIdObj instanceof Number) {
                return ((Number) userIdObj).intValue();
            }
            
            // Intentar convertir a String y luego a Integer
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