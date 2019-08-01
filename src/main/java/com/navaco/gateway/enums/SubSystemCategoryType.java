package com.navaco.gateway.enums;

public enum SubSystemCategoryType {
    ARZI("ARZI"),
    CHANNEL_MANAGER("CHANNEL_MANAGER");

    private String categoryName;

    private SubSystemCategoryType(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
