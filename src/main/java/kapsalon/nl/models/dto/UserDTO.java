package kapsalon.nl.models.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;


@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserDTO {
    private Long id;

    private String role;

    private LocalDate registerDate;

    private String password;
}
