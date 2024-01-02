package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="financial_details")
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    private String accountName;
    private String accountNumber;
    private String cardNumber;
    private String valid;
}
