package com.amigoscode.customer;

public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");

    private final String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
