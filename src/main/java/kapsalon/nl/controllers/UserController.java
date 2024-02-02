package kapsalon.nl.controllers;


import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.UserDto;
import kapsalon.nl.repo.AuthorityRepository;
import kapsalon.nl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> userDtos = userService.getAllUsers();

        return ResponseEntity.ok().body(userDtos);
    }



    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getByByUserName(@PathVariable String username) {

            UserDto optionalUser = userService.getByUserName(username);
            return ResponseEntity.ok().body(optionalUser);

    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> createKlant(@RequestBody UserDto dto) {;

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);


        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_CUSTOMER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(newUsername);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        UserDto updatedUser = userService.updateUser(username, dto);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserRoles(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {

            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}