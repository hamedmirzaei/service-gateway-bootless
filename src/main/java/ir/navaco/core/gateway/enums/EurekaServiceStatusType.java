package ir.navaco.core.gateway.enums;

public enum EurekaServiceStatusType {
    PUBLISHED("PUBLISHED"),
    TESTED("TESTED"),
    INACTIVE("INACTIVE");

    private String statusName;

    EurekaServiceStatusType(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
