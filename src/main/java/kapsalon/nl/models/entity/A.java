package kapsalon.nl.models.entity;

import jakarta.persistence.*;
import lombok.*;





@MappedSuperclass

public class A {

    @Id
    private long personId;
    private String name;
}
