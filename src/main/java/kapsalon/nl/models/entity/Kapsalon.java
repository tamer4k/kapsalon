package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String owner;
    private String location;
    private String postalCode;
    private boolean availability;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opening_hours_id")
    private OpeningHours openingHours;


}
