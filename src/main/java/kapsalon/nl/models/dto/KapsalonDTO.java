package kapsalon.nl.models.dto;
import kapsalon.nl.models.entity.OpeningHours;
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

public class KapsalonDTO {
    private Long id;

    @NotBlank(message = "het mag niet leeg zijn")
    private String name;

    @NotBlank(message = "het mag niet leeg zijn")
    private String location;

    @NotBlank(message = "het mag niet leeg zijn")
    @Pattern(regexp = "\\d{4}[A-Za-z]{2}", message = "Ongeldige postcode, gebruik het formaat 1234AB")
    private String postalCode;


    private boolean availability;

    private OpeningHours openingHours;
}
