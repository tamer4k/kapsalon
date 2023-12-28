package kapsalon.nl.models.dto;

import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kapsalon.nl.models.entity.OpeningsTijden;
import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KapsalonDTO {
    private Long id;
    private String name;
    private String location;
    private boolean availability;
    private OpeningsTijden openingsTijden;
}
