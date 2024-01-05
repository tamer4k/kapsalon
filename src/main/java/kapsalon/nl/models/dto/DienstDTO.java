package kapsalon.nl.models.dto;
import jakarta.validation.constraints.*;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Kapper;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class DienstDTO {
    private Long id;

    @Pattern(regexp = "Men|Women|Children", message = "Ongeldige enum-waarde, je kan een van die drie category kiezen (Men,Women,Children)")
    private String category;

    @NotBlank(message = "deze mag niet leeg zijn")
    private String title;

    @NotBlank(message = "deze mag niet leeg zijn")
    @Size(min = 2, max = 50, message = "Lengte moet tussen 2 en 50 liggen")
    private String description;

    @Positive(message = "Moet een positieve waarde zijn")
    private double duration;

    @PositiveOrZero(message = "Moet een positieve waarde of nul zijn")
    private double price;
}
