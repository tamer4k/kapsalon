package kapsalon.nl.models.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.models.entity.Klant;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentDTO {
    private Long id;
    private Kapsalon kapsalon;
    private Dienst dienst;
    private Kapper kapper;
    private Date appointmentDate;
    private Time appointmentTime;
    private Klant klant;
}
