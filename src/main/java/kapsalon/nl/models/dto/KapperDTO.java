package kapsalon.nl.models.dto;

import jakarta.persistence.*;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.Kapsalon;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private Kapsalon kapsalon;
    private List<Dienst> diensten = new ArrayList<>();
}
