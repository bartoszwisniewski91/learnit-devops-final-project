package com.devops.app;

import com.devops.app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoApplication {

    private final UserService userService;

    public DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Endpoint do rejestracji (dostępny publicznie)
    @PostMapping("/public/register")
    public String register(@RequestParam String user, @RequestParam String pass) {
        userService.register(user, pass);
        return "Konto założone dla: " + user + ". Możesz się teraz zalogować!";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails user) {
        return "Witaj " + user.getUsername() + "! Twoje konto jest aktywne i pobrane z bazy PostgreSQL.";
    }
}