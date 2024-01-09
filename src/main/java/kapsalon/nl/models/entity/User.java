package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
}
