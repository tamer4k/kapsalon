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
    private Date appointmentDate;
    private Time appointmentTime;

    @ManyToOne()
    @JoinColumn(name = "kapper_id")
    private Kapper kapper;

    @ManyToOne()
    @JoinColumn(name = "dienst_id")
    private Dienst dienst;
}
