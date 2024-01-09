package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;

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
    @JoinColumn(name = "barber_id")
    private Barber barber;

    private LocalDate appointmentDate;

    private Time appointmentTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
