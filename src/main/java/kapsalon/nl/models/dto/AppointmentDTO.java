package kapsalon.nl.models.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.Kapsalon;
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
    private Date appointmentDate;
    private Time appointmentTime;
    private Kapper kapper;
    private Dienst dienst;
}
