package kapsalon.nl.models.entity;

public enum Status {

    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");
    private final String displayName;
    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
