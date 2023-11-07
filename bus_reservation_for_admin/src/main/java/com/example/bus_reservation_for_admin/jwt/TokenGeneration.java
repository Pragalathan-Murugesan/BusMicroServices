package com.example.bus_reservation_for_admin.jwt;

import com.example.bus_reservation_for_admin.dto.LoginDto;
import com.example.bus_reservation_for_admin.exception.IlllegalAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGeneration {
    private  static  final String  privateKey= "This Private Key is Admin";
    Long takenTime = System.currentTimeMillis();
    Long expiredTime =takenTime + (5*60*1000);
    Date takenAt = new Date(takenTime);
    Date expiredAt = new Date(expiredTime);
    public String generateTokens(LoginDto loginDto){
        Claims claims = Jwts.claims()
                .setExpiration(expiredAt)
                .setIssuedAt(takenAt)
                .setIssuer(loginDto.getRole());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS384,privateKey).compact();

    }
    public Claims verifyToken(String authorization) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(authorization).getBody();
            return claims;
        } catch (IllegalArgumentException e) {
            throw new IlllegalAuthException();
        }
    }
}

