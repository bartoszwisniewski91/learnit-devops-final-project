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

    public DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostMapping("/public/register")
    public void register(@RequestParam String user, 
                         @RequestParam String pass, 
                         HttpServletResponse response) throws IOException {
        try {
            userService.register(user, pass);
            String message = URLEncoder.encode("Sukces", StandardCharsets.UTF_8);
            response.sendRedirect("/login?success=" + message);
        } catch (Exception e) {
            String error = URLEncoder.encode("Blad", StandardCharsets.UTF_8);
            response.sendRedirect("/register.html?error=" + error);
        }
    }

    @GetMapping("/public/status")
    public String status() {
        return "Serwer dziala poprawnie. Polskie znaki: aezzcclons.";
    }
}