package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name="dienst")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Dienst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private double duration;
    private double price;
}
