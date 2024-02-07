package kapsalon.nl.models.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;


@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateAppointmentByOwnerDTO {

    private Long id;

    private String status;

    private  boolean isPaid;

}
