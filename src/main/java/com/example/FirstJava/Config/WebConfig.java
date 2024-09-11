package com.example.FirstJava.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Mengizinkan semua endpoint
                        .allowedOrigins("http://localhost:63342/") // Izinkan domain tertentu
                        .allowedMethods("GET", "POST", "PATCH", "DELETE") // Metode HTTP yang diizinkan
                        .allowedHeaders("*") // Header yang diizinkan
                        .allowCredentials(true) // Izinkan kredensial (misalnya, cookies)
                        .maxAge(3600); // Maksimal waktu caching (detik)
            }
        };
    }
}
