package kapsalon.nl.models.entity;

public enum Status {

    OPEN("OPEN"),
    APPROVED("APPROVED"),
    REFUSED("REFUSED");
    private final String displayName;
    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
