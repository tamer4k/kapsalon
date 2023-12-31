package kapsalon.nl.models.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="barbers")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Barber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean available;
    private String license;

    @ManyToOne
    @JoinColumn(name = "kapsalon_id")
    private Kapsalon kapsalon;

    @ManyToMany
    @JoinTable(
            name = "barber_dienst",
            joinColumns = @JoinColumn(name = "barber_id"),
            inverseJoinColumns = @JoinColumn(name = "dienst_id")
    )
    private List<Dienst> diensten = new ArrayList<>();


}
