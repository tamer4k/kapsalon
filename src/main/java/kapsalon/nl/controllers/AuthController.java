package kapsalon.nl.controllers;


import kapsalon.nl.models.dto.LoginDTO;
import kapsalon.nl.models.dto.RegisterDTO;
import kapsalon.nl.models.entity.Role;
import kapsalon.nl.models.entity.UserEntity;
import kapsalon.nl.repo.RoleRepository;
import kapsalon.nl.repo.UserRepository;

import kapsalon.nl.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")

public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    @Autowired
    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
            this.authManager = authManager;
            this.jwtService = jwtService;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.encoder = encoder;
    }



        @PostMapping("/register")
        public String register(@RequestBody RegisterDTO registerDTO) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(registerDTO.getUsername());
            newUser.setPassword(encoder.encode(registerDTO.getPassword()));

            List<Role> userRoles = newUser.getRoles();
            for (String rolename : registerDTO.roles) {
                Optional<Role> or = roleRepository.findByRolename(rolename);
                if (or.isPresent()) {
                    userRoles.add(or.get());
                }
            }

            userRepository.save(newUser);

            return "Done";
        }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }



}

