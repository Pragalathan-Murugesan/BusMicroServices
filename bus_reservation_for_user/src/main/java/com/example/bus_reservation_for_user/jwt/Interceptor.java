package com.example.bus_reservation_for_user.jwt;

import com.example.bus_reservation_for_user.exception.IlllegalAuthException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class Interceptor implements HandlerInterceptor {
    @Autowired
    private TokenGeneration tokenGeneration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwts = null;
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            jwts = token.substring(7, token.length());
        }

        if (!(request.getRequestURI().contains("/api/user/signup")||request.getRequestURI().contains("api/user/login"))){
            try {
                Claims claims = tokenGeneration.verifyToken(jwts);

                if (claims.getIssuer().equals("Admin")) {
                    if (!request.getRequestURI().contains("/admin/api")) {
                        throw new IlllegalAuthException();
                    }

                } else if (claims.getIssuer().equals("User")) {
                    if (!request.getRequestURI().contains("/user/api")) {
                        throw new IlllegalAuthException();
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new IlllegalAuthException();
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
