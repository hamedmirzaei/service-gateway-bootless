package com.navaco.gateway.enums;

public enum EurekaServiceStatusType {
    PUBLISHED("PUBLISHED"),
    TESTED("TESTED"),
    INACTIVE("INACTIVE");

    private String statusName;

    private EurekaServiceStatusType(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
