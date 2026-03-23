package com.devops.app;

import com.devops.app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = "com.devops.app")
public class DemoApplication {

    private final UserService userService;

    public DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostMapping("/public/register")
    public void register(@RequestParam String user, @RequestParam String pass, HttpServletResponse response) throws IOException {
        userService.register(user, pass);
        // Po rejestracji wysyłamy użytkownika do strony logowania
        response.sendRedirect("/login?registered=true");
    }

    @GetMapping("/")
    public Map<String, Object> home(@AuthenticationPrincipal UserDetails user) {
        Map<String, Object> response = new HashMap<>();
        response.put("wiadomosc", "Witaj " + user.getUsername() + "!");
        response.put("system", "DevOps Pipeline OK");
        return response;
    }
}