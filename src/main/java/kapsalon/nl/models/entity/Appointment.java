package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Table(name="appointment")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "kapsalon_id")
    private Kapsalon kapsalon;

    @ManyToOne()
    @JoinColumn(name = "dienst_id")
    private Dienst dienst;

    @ManyToOne()
    @JoinColumn(name = "kapper_id")
    private Kapper kapper;

    private Date appointmentDate;

    private Time appointmentTime;

    @ManyToOne
    @JoinColumn(name = "klant_id")
    private Klant klant;



}
