package kapsalon.nl.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name="openings_tijden")
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpeningsTijden {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String monday;

    private String tuesday;

    private String wednesday;

    private String thursday;

    private String friday;

    private String saturday;

    private String sunday;


}



