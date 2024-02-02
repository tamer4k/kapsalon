package kapsalon.nl.models.dto;

import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Barber;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.models.entity.User;
import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.sql.Time;
import java.time.LocalDate;

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
    private Kapsalon selectedKapsalon;

    @NotNull(message = "het mag niet leeg zijn ")
    private Dienst selectedDienst;

    @NotNull(message = "het mag niet leeg zijn")
    private Barber selectedBarber;

    @NotNull(message = "het mag niet leeg zijn")
    @Future(message = "Datum moet in de toekomst liggen")
    private LocalDate appointmentDate;


    @NotNull(message = "het mag niet leeg zijn")
    private Time appointmentTime;

    @NotNull(message = "het mag niet leeg zijn")
    private User user;

    private  boolean isPaid;


}
