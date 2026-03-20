package com.devops.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.HashMap;

@SpringBootApplication
@RestController // Dodajemy to, aby klasa mogła obsługiwać zapytania HTTP
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Strona główna - dostępna tylko po zalogowaniu
    @GetMapping("/")
    public Map<String, Object> home(@AuthenticationPrincipal UserDetails user) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Witaj w zabezpieczonym systemie DevOps!");
        response.put("user", user.getUsername());
        response.put("roles", user.getAuthorities());
        response.put("status", "Połączono z bazą PostgreSQL");
        return response;
    }

    // Publiczny endpoint do testów monitoringu (dostępny bez logowania przez SecurityConfig)
    @GetMapping("/public/status")
    public String status() {
        return "Serwer działa poprawnie - tryb publiczny";
    }
}