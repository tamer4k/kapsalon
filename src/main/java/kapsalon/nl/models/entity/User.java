package kapsalon.nl.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name="users")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private LocalDate registerDate;

    private String password;

}
