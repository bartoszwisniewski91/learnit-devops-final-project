package com.devops.app;

import com.devops.app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.HashMap;

@SpringBootApplication
@RestController
// Wymuszamy skanowanie wszystkich podpakietów (config, service, repository)
@ComponentScan(basePackages = "com.devops.app") 
public class DemoApplication {

    private final UserService userService;

    // Konstruktor - Spring automatycznie wstrzyknie tutaj UserService
    public DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Endpoint do rejestracji nowego użytkownika.
     * Dostępny publicznie dzięki konfiguracji w SecurityConfig.
     * Przykład użycia: POST http://IP:8080/public/register?user=mojlogin&pass=mojehaslo
     */
    @PostMapping("/public/register")
    public String register(@RequestParam String user, @RequestParam String pass) {
        try {
            userService.register(user, pass);
            return "Konto zalozone pomyslnie dla uzytkownika: " + user;
        } catch (Exception e) {
            return "Blad podczas rejestracji: " + e.getMessage();
        }
    }

    /**
     * Strona główna dostępna tylko dla zalogowanych użytkowników.
     * Wyświetla dane zalogowanej osoby pobrane z bazy danych.
     */
    @GetMapping("/")
    public Map<String, Object> home(@AuthenticationPrincipal UserDetails user) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Sukces");
        response.put("message", "Witaj w zabezpieczonym systemie DevOps!");
        response.put("zalogowany_uzytkownik", user.getUsername());
        response.put("autoryzacja", "Pobrana z PostgreSQL");
        return response;
    }

    /**
     * Publiczny status serwera dla monitoringu.
     */
    @GetMapping("/public/status")
    public String status() {
        return "Serwer działa i jest dostępny publicznie.";
    }
}