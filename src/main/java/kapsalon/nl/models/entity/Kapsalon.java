package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Table(name="kapsalon")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Kapsalon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private boolean availability;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "openings_tijden_id")
    private OpeningsTijden openingsTijden;

}
