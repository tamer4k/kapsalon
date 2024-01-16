package kapsalon.nl.models.dto;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated

public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
