package com.devops.app;

import com.devops.app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = "com.devops.app")
public class DemoApplication {

    private final UserService userService;

    // Konstruktor wstrzykujący serwis użytkowników
    public DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Endpoint rejestracji:
     * 1. Przyjmuje dane z formularza register.html (POST).
     * 2. Rejestruje użytkownika w bazie PostgreSQL.
     * 3. Przekierowuje do strony logowania z polskim komunikatem o sukcesie.
     */
    @PostMapping("/public/register")
    public void register(@RequestParam String user, 
                         @RequestParam String pass, 
                         HttpServletResponse response) throws IOException {
        try {
            userService.register(user, pass);
            
            // Przygotowanie polskiego komunikatu do wyświetlenia w URL (opcjonalnie)
            String message = URLEncoder.encode("Rejestracja pomyślna! Możesz się zalogować.", StandardCharsets.UTF_8);
            response.sendRedirect("/login?success=" + message);
            
        } catch (Exception e) {
            // W razie błędu (np. użytkownik już istnieje) wracamy do rejestracji
            String error = URLEncoder.encode("Błąd: " + e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect("/register.html?