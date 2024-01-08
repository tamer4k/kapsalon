package kapsalon.nl.models.dto;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapsalon;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated


public class KapperDTO {
    private Long id;

    @NotBlank(message = "deze mag niet leeg zijn")
    private String name;

    private boolean available;

    @NotBlank(message = "deze mag niet leeg zijn")
    private String license;

    private Kapsalon kapsalon;

    private List<Dienst> diensten = new ArrayList<>();
}
