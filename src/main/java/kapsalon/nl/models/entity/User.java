package kapsalon.nl.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String username;

    private String password;

    private LocalDate registerDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();


//    @ManyToMany
//    @JoinTable(
//            name = "barber_dienst",
//            joinColumns = @JoinColumn(name = "barber_id"),
//            inverseJoinColumns = @JoinColumn(name = "dienst_id")
//    )
//    private List<Dienst> diensten = new ArrayList<>();
}
