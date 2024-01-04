package kapsalon.nl.models.dto;

import jakarta.persistence.*;
import kapsalon.nl.models.entity.Appointment;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.OpeningsTijden;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
