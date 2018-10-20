package model;

public enum ContactType {
    PHONE ("Тел."),
    SKYPE ("Skype"),
    EMAIL ("Почта"),
    LINKEDIN ("Профиль LinkedIn"),
    GITHUB ("Профиль GitHub"),
    STACKOVERFLOW ("Профиль Stackoverflow"),
    HOMEPAGE ("Домашняя страница");

    private String contactName;
    ContactType(String contactName) {
        this.contactName = contactName;
    }

    public String getTitle() {
        return contactName;
    }
}
