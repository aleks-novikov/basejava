import model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class ResumeInformationFilling {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME = new Resume(UUID_1, "Новиков Александр");

    public static void main(String[] args) {
        RESUME.setContact(ContactType.PHONE, "952-345-54-67");
        RESUME.setContact(ContactType.SKYPE, "alex-nov");

        Link link = new Link("https://www.linkedin.com/in/александр-новиков-b0326116a/", "linkedin");
        RESUME.setContact(ContactType.LINKEDIN, link.getName());
        link = new Link("https://github.com/alexNov13/basejava", "github");
        RESUME.setContact(ContactType.GITHUB, link.getName());

        RESUME.setSection(SectionType.OBJECTIVE, new TextSection("Junior Java-разработчик"));
        RESUME.setSection(SectionType.PERSONAL, new TextSection("Ответственность, стремление к постоянному развитию и упорство при достижении поставленных целей."));

        List<String> achievements = new LinkedList<>();
        achievements.add("2018 г. - разрабатываю Web-приложение База данных резюме на курсе baseJava.");
        achievements.add("2017-2018 гг. - в процессе работы в Сбербанке создал несколько проектов для сбора и обработки данных на языке VBA.");
        RESUME.setSection(SectionType.ACHIEVEMENT, new ListSection(achievements));

        List<String> skills = new LinkedList<>();
        skills.add("Intellij IDEA");
        skills.add("Git");
        skills.add("HTML/CSS");
        skills.add("XML");
        skills.add("SQL");
        skills.add("Java 8 SE");
        skills.add("ООП");
        skills.add("JUnit");
        skills.add("Английский язык уровня Intermediate");
        RESUME.setSection(SectionType.QUALIFICATIONS, new ListSection(skills));

        List<Organization> organizationsList = new LinkedList<>();
        Organization organization = new Organization("Сбербанк", "https://www.sberbank.ru/ru/person",
                LocalDate.of(2017, Month.AUGUST, 1), LocalDate.of(2018, Month.AUGUST, 13), "Программист VBA",
                "Разрабатывал программы на VBA для автоматизации сбора и обработки данных в MS Excel, Access, Outlook");
        organizationsList.add(organization);

        organization = new Organization("Ниеншанц-Автоматика", "https://nnz-ipc.ru",
                LocalDate.of(2017, Month.JUNE, 19), LocalDate.of(2017, Month.JUNE, 30),
                "Администратор баз данных", "За 2 недели обработал около 20 000 наименований товаров в Excel.");
        organizationsList.add(organization);
        RESUME.setSection(SectionType.EXPERIENCE, new OrganizationSection(organizationsList));

        List<Organization> educationList = new LinkedList<>();
        organization = new Organization("baseJava", "http://javaops.ru/reg/basejava",
                LocalDate.of(2018, Month.SEPTEMBER, 10), LocalDate.now(), "Junior Java-разработчик",
                "Разрабатываю Web-приложение База данных резюме с использованием Java, IntellijIDEA, GitHub, JUnit");
        educationList.add(organization);

        organization = new Organization("Санкт-Петербургский политехнический университет Петра Великого", "http://www.spbstu.ru",
                LocalDate.of(2014, Month.SEPTEMBER, 1), LocalDate.of(2018, Month.JUNE, 14),
                "Высшая школа управления и бизнеса, Информатик-экономист",  "");
        educationList.add(organization);
        RESUME.setSection(SectionType.EDUCATION, new OrganizationSection(educationList));

        RESUME.getResumeInformation(UUID_1);
    }
}
