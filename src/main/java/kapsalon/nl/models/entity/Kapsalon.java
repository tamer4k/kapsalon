package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "kapsalon_kapper",
            joinColumns = @JoinColumn(name = "kapsalon_id"),
            inverseJoinColumns = @JoinColumn(name = "kapper_id")
    )
    private Set<Kapper> kappers = new HashSet<>();

}
