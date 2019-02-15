package ru.topjava.basejava.util;

import ru.topjava.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ResumeTestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = new Resume(UUID_1, "Name1");
    public static final Resume RESUME_2 = new Resume(UUID_2, "Name2");
    public static final Resume RESUME_3 = new Resume(UUID_3, "Name3");
    public static final Resume RESUME_4 = new Resume(UUID_4, "Name4");

    static {
        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        for (Resume resume : resumes) {
            resume.setContact(ContactType.PHONE, "952-345-54-67");
            resume.setContact(ContactType.SKYPE, "alex-nov");
            resume.setSection(SectionType.OBJECTIVE, new TextSection("Junior Java-разработчик"));
            resume.setSection(SectionType.PERSONAL, new TextSection("Ответственность, стремление к постоянному развитию и упорство при достижении поставленных целей."));
            resume.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                    "2018 г. - разрабатываю Web-приложение База данных резюме на курсе baseJava.",
                    "2017-2018 гг. - в процессе работы в Сбербанке создал несколько проектов для сбора и обработки данных на языке VBA.")));
            resume.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Java 8 SE", "Intellij IDEA", "Git")));

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

            resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(organizationsList));
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
            resume.setSection(SectionType.EDUCATION, new OrganizationSection(educationList));
        }
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