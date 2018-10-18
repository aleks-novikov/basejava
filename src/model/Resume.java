package model;

import java.util.*;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private Contacts contacts;
    private ObjectiveAndPersonal objectiveAndPersonal;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid mustn't be null");
        Objects.requireNonNull(fullName, "fullName mustn't be null");
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new Contacts();
        objectiveAndPersonal = new ObjectiveAndPersonal();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        return fullName.compareTo(o.fullName);
    }

    //    можно хранить список секций в коллекции
    //    так же сделать с Контактами
    // посмотреть, какие секции схожие. Сделать для них одинаковую реализацию
    // контакты и секции выводить расширенным циклом for

    public void addResumeContacts(String key, String value) {
        contacts.addContacts(key, value);
    }

    public void getContacts(){
        contacts.getInfo();
    }

    class Contacts {
        private Map<String, String> contacts;
        private List<String> links;

        public Contacts() {
            contacts = new HashMap<>();
            links = new ArrayList<>();
        }

        public void addContacts(String key, String val) {
            if (key != null) {
                contacts.put(key, val);
            } else {
                links.add(val);
            }
        }

        public void getInfo() {
            System.out.println("Контакты: ");
            for (Map.Entry entry : contacts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("Ссылки: ");
            for (String info : links) {
                System.out.println(info);
            }
        }
    }

    class ObjectiveAndPersonal {

    }
}