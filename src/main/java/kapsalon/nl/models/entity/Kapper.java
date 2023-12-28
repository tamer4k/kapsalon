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

    @ManyToMany(mappedBy = "kappers")
    private Set<Kapsalon> kapsalons = new HashSet<>();

    @OneToMany(mappedBy = "kapper")
    private Set<Dienst> diensten = new HashSet<>();

}
