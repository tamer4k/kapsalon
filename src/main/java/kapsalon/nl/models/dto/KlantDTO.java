package kapsalon.nl.models.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated

public class KlantDTO {
    private Long id;

    private String firstName;

    private String secondName;

    @Email(message = "Ongeldig e-mailadres")
    private String email;

    @NotBlank(message = "Telefoonnummer mag niet leeg zijn")
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Ongeldig telefoonnummer formaat")
    private String phoneNumber;
}
