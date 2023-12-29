package kapsalon.nl.models.dto;

import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class KlantDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
}
