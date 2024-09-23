package org.sparta.outsourcingproject.domain.store.enums;

public enum StoreOperationStatus {

    OPERATE("정상 영업 중"),
    SHUTDOWN("폐업");

    private final String description;

    StoreOperationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
