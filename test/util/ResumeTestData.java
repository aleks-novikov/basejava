package util;

import model.AbstractSection;
import model.ContactType;
import model.Resume;
import model.SectionType;

import java.util.Map;

public class ResumeTestData {
    public static Resume addResumeInfo(Resume resume) {
        resume.addContact(ContactType.PHONE, "952-345-54-67");
        resume.addContact(ContactType.SKYPE, "alex-nov");
       /* resume.addSection(SectionType.OBJECTIVE, new TextSection("Junior Java-разработчик"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Ответственность, стремление к постоянному развитию и упорство при достижении поставленных целей."));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                "2018 г. - разрабатываю Web-приложение База данных резюме на курсе baseJava.",
                "2017-2018 гг. - в процессе работы в Сбербанке создал несколько проектов для сбора и обработки данных на языке VBA.")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Java 8 SE", "Intellij IDEA", "Git")));

        List<Organization> organizationsList = new LinkedList<>();

        Organization organization = new Organization("Сбербанк", "https://www.sberbank.ru/ru/person");
        organization.addOrganizationInfo(LocalDate.of(2017, Month.AUGUST, 1), LocalDate.of(2018, Month.AUGUST, 1),
                "Программист VBA", "Разрабатывал программы на VBA для автоматизации сбора и обработки данных в MS Excel, Access, Outlook.");
        organizationsList.add(organization);

        organization = new Organization("Ниеншанц-Автоматика", "https://nnz-ipc.ru");
        organization.addOrganizationInfo(LocalDate.of(2017, Month.JUNE, 1), LocalDate.of(2017, Month.JULY, 1),
                "Администратор баз данных", "За 2 недели обработал около 20 000 наименований товаров в Excel.");
        organization.addOrganizationInfo(LocalDate.of(2016, Month.JUNE, 1), LocalDate.of(2016, Month.JULY, 1),
                "Администратор баз данных", "За 2 недели завёл и разместил более 800 товарных карточек на сайте компании.");
        organizationsList.add(organization);

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(organizationsList));
        List<Organization> educationList = new LinkedList<>();
        organization = new Organization("baseJava", "http://javaops.ru/reg/basejava");
        organization.addOrganizationInfo(LocalDate.of(2018, Month.SEPTEMBER, 1), LocalDate.of(2019, Month.JANUARY, 1),
                "Junior Java-разработчик",
                "Разрабатываю Web-приложение База данных резюме с использованием Java, IntellijIDEA, GitHub, JUnit");
        educationList.add(organization);

        organization = new Organization("Санкт-Петербургский политехнический университет Петра Великого", "http://www.spbstu.ru");
        organization.addOrganizationInfo(LocalDate.of(2014, Month.SEPTEMBER, 1), LocalDate.of(2018, Month.JUNE, 1),
                "Высшая школа управления и бизнеса, Информатик-экономист", null);
        educationList.add(organization);
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(educationList));*/
        return resume;
    }

    public static void getResumeInfo(Resume resume) {
        System.out.println(resume.getFullName());
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
    }
}