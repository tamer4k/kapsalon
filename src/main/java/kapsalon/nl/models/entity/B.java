package kapsalon.nl.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;



@Entity

public class B  extends A {
    @Id
    private long companyId;
    private String company;

}
