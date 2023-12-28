package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.Kapsalon;
import lombok.*;

@Entity
@Table(name = "kapsalon_kapper")
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class KapsalonKapper{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kapsalon_id")
    private Kapsalon kapsalon;

    @ManyToOne
    @JoinColumn(name = "kapper_id")
    private Kapper kapper;


}