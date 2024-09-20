package org.sparta.outsourcingproject.domain.store.enums;

public enum StoreStatus {
    OPENED("개점"),
    CLOSED("마감");

    private final String description;

    StoreStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
