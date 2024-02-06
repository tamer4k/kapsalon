package kapsalon.nl.models.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Kapsalon kapsalon;

    @ManyToMany
    @JoinTable(
            name = "barber_dienst",
            joinColumns = @JoinColumn(name = "barber_id"),
            inverseJoinColumns = @JoinColumn(name = "dienst_id")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Dienst> diensten = new ArrayList<>();


    @OneToOne(mappedBy = "barber")
    private ImageData imageData;

}
