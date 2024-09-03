package com.amigoscode.customer;

public enum SortCriteria {
    ID("id"),
    AGE("age"),
    GENDER("gender"),
    IMAGE("profileImageId");

    private final String fieldName;

    SortCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}