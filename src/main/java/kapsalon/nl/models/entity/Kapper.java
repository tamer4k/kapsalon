package kapsalon.nl.models.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="kappers")
@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Kapper {
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
            name = "kapper_dienst",
            joinColumns = @JoinColumn(name = "kapper_id"),
            inverseJoinColumns = @JoinColumn(name = "dienst_id")
    )
    private List<Dienst> diensten = new ArrayList<>();


}
