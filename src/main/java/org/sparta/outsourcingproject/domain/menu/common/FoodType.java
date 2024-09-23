package org.sparta.outsourcingproject.domain.menu.common;

public enum FoodType {
    KOREAN("한식"),
    CHINESE("중식"),
    SNACK("분식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    FASTFOOD("패스트푸드");

    private final String description;

    FoodType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
