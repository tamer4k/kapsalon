package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


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

    @ManyToMany
    @JoinTable(
            name = "dienst_kapper",
            joinColumns = @JoinColumn(name = "dienst_id"),
            inverseJoinColumns = @JoinColumn(name = "kapper_id")
    )

    private Set<Kapper> kappers = new HashSet<>();

}
