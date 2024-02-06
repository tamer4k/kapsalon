package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name="requested_owner_role")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestedOwnerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Status status;
}
