package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Kapsalon selectedKapsalon;

    @ManyToOne()
    @JoinColumn(name = "dienst_id")
    private Dienst selectedDienst;

    @ManyToOne()
    @JoinColumn(name = "barber_id")
    private Barber selectedBarber;

    private LocalDate appointmentDate;

    private Time appointmentTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private  boolean isPaid;



}
