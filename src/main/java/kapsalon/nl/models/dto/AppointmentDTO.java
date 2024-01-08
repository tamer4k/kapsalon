package kapsalon.nl.models.dto;

import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.models.entity.Klant;
import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AppointmentDTO {
    private Long id;

    @NotNull(message = "het mag niet leeg zijn")
    private Kapsalon kapsalon;

    @NotNull(message = "het mag niet leeg zijn ")
    private Dienst dienst;

    @NotNull(message = "het mag niet leeg zijn")
    private Kapper kapper;

    @NotNull(message = "het mag niet leeg zijn")
    @Future(message = "Datum moet in de toekomst liggen")
    private LocalDate appointmentDate;


    @NotNull(message = "het mag niet leeg zijn")
    private Time appointmentTime;

    @NotNull(message = "het mag niet leeg zijn")
    private Klant klant;

}
