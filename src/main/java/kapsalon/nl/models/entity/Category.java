package kapsalon.nl.models.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


public enum Category {
    Men("Men"),
    Women("Women"),
    Children("Children");
    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
