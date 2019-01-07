package storage.serialization;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());  //число записываемых контактов

            //запись контактов в файл
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            //запись в файл остальных секций
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());

                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = (ListSection) entry.getValue();
                        List<String> values = ls.getList();
                        dos.writeInt(values.size());

                        for (String data : values) {
                            dos.writeUTF(data);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection orgSec = (OrganizationSection) entry.getValue();
                        List<Organization> organisations = orgSec.getOrganisations();

                        //число организаций для последующего чтения
                        dos.writeInt(organisations.size());

                        for (Organization o : organisations) {
                            dos.writeUTF(o.getHomePage().getName());
                            dos.writeUTF(o.getHomePage().getUrl());
                            dos.writeInt(o.getPositions().size());   //число позиций данной организации для последующего чтения

                            for (Organization.Position position : o.getPositions()) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                }
            }
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();   //число записанных ранее контактов
            for (int i = 0; i < size; i++) {    //чтение контактов
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            sectionsDataRead(resume, dis);
            return resume;
        }
    }

    private void sectionsDataRead(Resume resume, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        AbstractSection sectionData = null;

        for (int i = 0; i < size; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    sectionData = new TextSection(dis.readUTF());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    int dataLinesAmount = dis.readInt();
                    List<String> dataList = new ArrayList<>(dataLinesAmount);

                    for (int j = 0; j < dataLinesAmount; j++) {
                        dataList.add(dis.readUTF());
                    }
                    sectionData = new ListSection(dataList);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    sectionData = readSectionData(dis);
            }
            resume.addSection(sectionType, sectionData);
        }
    }

    private AbstractSection readSectionData(DataInputStream dis) throws IOException {
        int organizationsAmount = dis.readInt();
        List<Organization> organizationsList = new ArrayList<>();
        Organization organization;

        for (int i = 0; i < organizationsAmount; i++) {
            String name = dis.readUTF();
            String link = dis.readUTF();
            organization = new Organization(name, link);

            int positionsAmount = dis.readInt();
            for (int j = 0; j < positionsAmount; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String position = dis.readUTF();
                String description = dis.readUTF();
                organization.addOrganizationInfo(startDate, endDate, position, description);
            }
            organizationsList.add(organization);
        }
        return new OrganizationSection(organizationsList);
    }
}