package ir.navaco.core.gateway.enums;

public enum SubSystemCategoryType {
    ARZI("ARZI"),
    CHANNEL_MANAGER("CHANNEL_MANAGER");

    private String categoryName;

    SubSystemCategoryType(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
