package kapsalon.nl.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name="roles")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rolename;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
