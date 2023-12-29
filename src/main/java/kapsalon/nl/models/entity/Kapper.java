package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name="kappers")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Kapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean available;
    private String license;

//    @ManyToOne(mappedBy = "kappers")
//    private Set<Kapsalon> kapsalons = new HashSet<>();

    @ManyToMany(mappedBy = "kappers")
    private Set<Dienst> diensten = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "kapsalon_id")
    private Kapsalon kapsalon;

}
