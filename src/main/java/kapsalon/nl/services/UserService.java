package kapsalon.nl.services;


import kapsalon.nl.exceptions.UserAlreadyExistsException;
import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.dto.UserDto;
import kapsalon.nl.models.entity.Authority;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.User;
import kapsalon.nl.repo.UserRepository;
import kapsalon.nl.utils.RandomStringGenerator;
import kapsalon.nl.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }


    public List<UserDto> getAllUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }


    public UserDto getByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("No user found with username: " + username));

        return fromUser(user);
    }
    private boolean userExistsByUsername(String username) {

        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    public String createUser(UserDto userDto) {

        if (userExistsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists");
        }

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        userDto.getPassword();
        User newUser = userRepository.save(toUser(userDto));
        newUser.setPassword(userDto.getPassword());
        return newUser.getUsername();
    }


    public void deleteUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("No user found with username: " + username));
        userRepository.delete(user);
    }

    public UserDto updateUser(String username, UserDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("No user found with username: " + username));

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setApikey(dto.getApikey());
        user.setEmail(dto.getEmail());
        user.setAuthorities(dto.getAuthorities());

        userRepository.save(user);
        return fromUser(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
