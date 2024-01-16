package kapsalon.nl.models.dto;

import kapsalon.nl.models.entity.Role;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RegisterDTO {
    private String username;
    private String password;
    public String[] roles;
}
