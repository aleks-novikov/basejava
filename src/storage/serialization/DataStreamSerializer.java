package storage.serialization;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
                dos.writeUTF(entry.getValue().toString());
                dos.writeUTF("next");
            }
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();  //число записанных контактов
            //чтение контактов
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            contactsRead(resume, dis);
            return resume;
        }
    }

    private void contactsRead(Resume resume, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        AbstractSection as = null;

        for (int i = 0; i < size; i++) {
            String line = dis.readUTF();
            if (line.equals("next")) {
                line = dis.readUTF();
            }

            SectionType section = SectionType.valueOf(line);
            switch (section) {
                case PERSONAL:
                case OBJECTIVE:
                    as = new TextSection(dis.readUTF());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> info = new ArrayList<>();
                    do {
                        info.add(dis.readUTF());
                    } while (!dis.readUTF().equals("next"));
                    as = new ListSection(info);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    String allData = dis.readUTF().replace("[", "").replace("]", "");
                    as = addInfo(allData);
            }
            resume.addSection(section, as);
        }
    }

    private AbstractSection addInfo(String allData) {
        List<String> dataList = new LinkedList<>(Arrays.asList(allData.split("\t")));
        Organization organization = null;
        List<Organization> organizationsList = new LinkedList<>();

        int i = 0;
        do {
            String url = null;
            String description = null;
            String name = dataList.get(i);

            if (name.startsWith(", 2") || name.startsWith("2")) {
                i = i - 2;
            } else {
                url = dataList.get(i + 1);
            }
            if (name.startsWith(", ")) {
                name = name.substring(2);
            }

            LocalDate startDate = getDate(dataList.get(i + 2));
            LocalDate endDate = getDate(dataList.get(i + 3));
            String position = dataList.get(i + 4);

            //поле description может быть не заполнено
            if (i + 5 < dataList.size()) {
                description = dataList.get(i + 5);
            }
            if (url != null) {
                organization = new Organization(name, url);
            }
            organization.addOrganizationInfo(startDate, endDate, position, description);

            if (!(new HashSet<>(organizationsList).contains(organization))) {
                organizationsList.add(organization);
            }

            i += 6;
        } while (i < dataList.size());
        return new OrganizationSection(organizationsList);
    }

    private LocalDate getDate(String data) {
        if (data.startsWith(", ")) {
            data = data.replace(", ", "");
        }
        return LocalDate.parse(data);
    }
}