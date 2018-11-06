package model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String contact;

    ContactType(String contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return contact;
    }
}
