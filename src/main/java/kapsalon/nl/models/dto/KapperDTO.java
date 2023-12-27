package kapsalon.nl.models.dto;

import jakarta.persistence.*;
import kapsalon.nl.models.entity.Category;
import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KapperDTO {
    private Long id;
    private String name;
    private boolean available;
    private String license;
}
