package kapsalon.nl.controllers;

import kapsalon.nl.models.entity.LoggedUser;
import kapsalon.nl.services.LoggedUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LoggedUserController {
    private final LoggedUserService loggedUserService;

    public LoggedUserController(LoggedUserService loggedUserService) {
        this.loggedUserService = loggedUserService;
    }


    @PostMapping("/login-attempt")
    public void saveLoginAttempt(@RequestParam String tryUsername, @RequestParam String tryPassword) {
        loggedUserService.saveLoginAttempt(tryUsername, tryPassword);
    }

    @GetMapping("/login-attempts")
    public List<LoggedUser> getAllLoginAttempts() {
        return loggedUserService.getAllLoginAttempts();
    }


}
