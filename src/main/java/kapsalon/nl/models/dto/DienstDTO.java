package kapsalon.nl.models.dto;

import kapsalon.nl.models.entity.Kapper;
import lombok.*;

import java.util.List;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class DienstDTO {
    private Long id;
    private String category;
    private String title;
    private String description;
    private double duration;
    private double price;
}
