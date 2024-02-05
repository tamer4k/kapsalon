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
public class RequestedOwnerRoleDTO {
    private Long id;
    private String username;
    private String status;
}
