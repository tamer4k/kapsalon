package kapsalon.nl.models.dto;

import jakarta.persistence.*;
import kapsalon.nl.models.entity.Role;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();


}
