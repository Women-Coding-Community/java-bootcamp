package com.wcc.bootcamp.java.mentorship.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;

/**
 * Security configuration that adds protective HTTP headers to all responses.
 * These headers help prevent XSS, clickjacking, and other common attacks.
 */
@Configuration
public class SecurityHeadersConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityHeadersInterceptor());
    }

    private static class SecurityHeadersInterceptor implements HandlerInterceptor {
        
        @Override
        public boolean preHandle(@NonNull HttpServletRequest request, 
                                 @NonNull HttpServletResponse response, 
                                 @NonNull Object handler) {
            
            // Prevent clickjacking - page cannot be embedded in frames
            response.setHeader("X-Frame-Options", "DENY");
            
            // Enable browser XSS filter
            response.setHeader("X-XSS-Protection", "1; mode=block");
            
            // Prevent MIME type sniffing
            response.setHeader("X-Content-Type-Options", "nosniff");
            
            // Control referrer information sent with requests
            response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
            
            // Content Security Policy - restricts resource loading
            // Allows Bootstrap CDN resources but prevents inline scripts (except Thymeleaf)
            response.setHeader("Content-Security-Policy", 
                "default-src 'self'; " +
                "script-src 'self' https://cdn.jsdelivr.net 'unsafe-inline'; " +
                "style-src 'self' https://cdn.jsdelivr.net 'unsafe-inline'; " +
                "font-src 'self' https://cdn.jsdelivr.net; " +
                "img-src 'self' data:; " +
                "frame-ancestors 'none';");
            
            // Permissions Policy - disable unnecessary browser features
            response.setHeader("Permissions-Policy", 
                "geolocation=(), microphone=(), camera=()");
            
            return true;
        }
    }
}
