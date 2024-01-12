package kapsalon.nl.models.entity;


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
