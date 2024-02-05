package kapsalon.nl.services;

import kapsalon.nl.models.entity.LoggedUser;
import kapsalon.nl.repo.LoggedUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggedUserService {
    private final LoggedUserRepository loggedUserRepository;

    public LoggedUserService(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }


    public void saveLoginAttempt(String tryUsername, String tryPassword) {
        LoggedUser loginAttempt = new LoggedUser();
        loginAttempt.setTryUsername(tryUsername);
        loginAttempt.setTryPassword(tryPassword);
        loginAttempt.setLoginTime(LocalDateTime.now());
        loggedUserRepository.save(loginAttempt);
    }

    public List<LoggedUser> getAllLoginAttempts() {
        return loggedUserRepository.findAll();
    }
}
