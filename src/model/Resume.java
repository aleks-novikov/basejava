package model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    /*
    private ResumeContacts cont;
    private AbstractSection section;
    private Section1 section1;
*/


   /* class ResumeContacts {
        public void addContacts(ContactType key, String value) {
            contacts.put(key, value);
        }

        public void getContacts() {
            for (Map.Entry entry : contacts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public void addResumeContacts(ContactType key, String value) {
        contacts.addContacts(key, value);
    }

    public void getResumeContact(ContactType contactType) {
        contactType.();
    }
*/

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid mustn't be null");
        Objects.requireNonNull(fullName, "fullName mustn't be null");
        this.uuid = uuid;
        this.fullName = fullName;
        /*cont = new ResumeContacts();
        section = new AbstractSection();
        section1 = new Section1();*/
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

    /*class AbstractSection {
        protected Map<SectionType, String> sectionInfoString = new HashMap<>();
        protected Map<SectionType, List<String>> sectionInfoList = new HashMap<>();
        protected Map<SectionType, Map<String, Map>> sectionInfoMap = new HashMap<>();

        public void setSectionData(SectionType sectionType, String info) {
            sectionInfoString.put(sectionType, info);
        }

        public void setSectionData(SectionType sectionType, List<String> info) {
            sectionInfoList.put(sectionType, info);
        }

        public void setSectionData(SectionType sectionType, Map<String, Map> info) {
            sectionInfoMap.put(sectionType, info);
        }

        public void getSectionData() {
            for (SectionType section : SectionType.values()) {
                System.out.println(section.getTitle() + ":");
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        output(section, sectionInfoString);
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        output(section, sectionInfoList);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        output(section, sectionInfoMap);
                }
            }
        }

        private void output(SectionType section, Map mapName) {
            System.out.println(mapName.get(section));
        }
    }


    public void setSectionData(SectionType sectionType, String data) {
//        if (data instanceof String) {
//            section.setSectionData(sectionType, (String) data);
//        } else if (data instanceof List) {
//            section.setSectionData(sectionType, (ArrayList) data);
//        } else if (data instanceof Map) {
//            section.setSectionData(sectionType, (HashMap) data);
//        }


                Map<String, Map> map = new HashMap<>();
        Map<String, String> info = new LinkedHashMap<>();
        info.put("date", "10/2013 - Сейчас");
        info.put("position", "Автор проекта");
        info.put("description", "Создание, организация и проведение Java онлайн проектов и стажировок");
        map.put("Java Online Projects", info);

        section.setSectionData(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        section.setSectionData(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
//        section.setSectionData(SectionType.EXPERIENCE, map);
//        getResumeInformation();
    }


    public void getResumeInformation() {
        System.out.println(fullName);
        getResumeContact();
        SectionType[] sections = SectionType.values();
        for (SectionType sect : sections) {
            section.getSectionData();
        }
    }*/
}