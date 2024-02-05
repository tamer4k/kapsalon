package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name="logged")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tryUsername;
    private String tryPassword;
    private LocalDateTime loginTime;

    public LoggedUser(String tryUsername, String tryPassword) {
        this.tryUsername = tryUsername;
        this.tryPassword = tryPassword;
    }

}
