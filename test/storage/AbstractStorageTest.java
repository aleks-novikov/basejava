package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected int oldStorageSize;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_1 = new Resume(UUID_1, "Name1");
    protected static final Resume RESUME_2 = new Resume(UUID_2, "Name2");
    protected static final Resume RESUME_3 = new Resume(UUID_3, "Name3");
    protected static final Resume RESUME_4 = new Resume(UUID_4, "Name4");

    static class ResumeTestData {
        private void addInfo() {
            Resume resume = new Resume(UUID_1, "Новиков Александр");
            resume.setContact(ContactType.PHONE, "952-345-54-67");
            resume.setContact(ContactType.SKYPE, "alex-nov");

            resume.setSection(SectionType.OBJECTIVE, new TextSection("Junior Java-разработчик"));
            resume.setSection(SectionType.PERSONAL, new TextSection("Ответственность, стремление к постоянному развитию и упорство при достижении поставленных целей."));
            resume.setSection(SectionType.ACHIEVEMENT, new ListSection("2018 г. - разрабатываю Web-приложение База данных резюме на курсе baseJava.", "2017-2018 гг. - в процессе работы в Сбербанке создал несколько проектов для сбора и обработки данных на языке VBA."));

            List<String> skills = new LinkedList<>();
            skills.add("Intellij IDEA");
            skills.add("Git");
            skills.add("Java 8 SE");
            skills.add("ООП");
            skills.add("JUnit");
            resume.setSection(SectionType.QUALIFICATIONS, new ListSection(skills));

            List<Organization> organizationsList = new LinkedList<>();

            Organization organization = new Organization("Сбербанк", "https://www.sberbank.ru/ru/person");
            organization.addOrganisationInfo(LocalDate.of(2017, Month.AUGUST, 1), LocalDate.of(2018, Month.AUGUST, 13),
                    "Программист VBA", "Разрабатывал программы на VBA для автоматизации сбора и обработки данных в MS Excel, Access, Outlook.");
            organizationsList.add(organization);

            organization = new Organization("Ниеншанц-Автоматика", "https://nnz-ipc.ru");
            organization.addOrganisationInfo(LocalDate.of(2017, Month.JUNE, 19), LocalDate.of(2017, Month.JUNE, 30),
                    "Администратор баз данных", "За 2 недели обработал около 20 000 наименований товаров в Excel.");
            organization.addOrganisationInfo(LocalDate.of(2016, Month.JUNE, 15), LocalDate.of(2016, Month.JUNE, 29),
                    "Администратор баз данных", "За 2 недели завёл и разместил более 800 товарных карточек на сайте компании.");
            organizationsList.add(organization);

            resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(organizationsList));
            List<Organization> educationList = new LinkedList<>();
            organization = new Organization("baseJava", "http://javaops.ru/reg/basejava");
            organization.addOrganisationInfo(LocalDate.of(2018, Month.SEPTEMBER, 10), LocalDate.now(),
                    "Junior Java-разработчик",
                    "Разрабатываю Web-приложение База данных резюме с использованием Java, IntellijIDEA, GitHub, JUnit");
            educationList.add(organization);

            organization = new Organization("Санкт-Петербургский политехнический университет Петра Великого", "http://www.spbstu.ru");
            organization.addOrganisationInfo(LocalDate.of(2014, Month.SEPTEMBER, 1), LocalDate.of(2018, Month.JUNE, 14),
                    "Высшая школа управления и бизнеса, Информатик-экономист", "");
            educationList.add(organization);
            resume.setSection(SectionType.EDUCATION, new OrganizationSection(educationList));
        }

        private void getResumeInformation(Resume resume) {
            System.out.println(resume.getFullName());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
            }

            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
            }
        }
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        oldStorageSize = storage.size();
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(oldStorageSize + 1, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(oldStorageSize - 1, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistStorageException() {
        storage.delete(UUID_4);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistStorageException() {
        storage.get(UUID_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "NewName");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        storage.update(RESUME_4);
    }

    @Test
    public void getAll() {
        assertEquals(3, storage.getAllSorted().size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test(expected = NullPointerException.class)
    public void setFullNameIncorrectNameException() {
        storage.save(new Resume("uuid5", null));
    }
}