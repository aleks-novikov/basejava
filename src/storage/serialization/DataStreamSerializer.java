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

            //чтение секций
            size = dis.readInt();
            AbstractSection as = null;
            for (int i = 0; i < size; i++) {
                String section = dis.readUTF();

                if (section.equals("next")) {
                    section = dis.readUTF();
                }

                switch (section) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        as = new TextSection(dis.readUTF());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> info = new ArrayList<>();
                        do {
                            info.add(dis.readUTF());
                        } while (!dis.readUTF().equals("next"));

                        as = new ListSection(info);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        String[] dataArray = dis.readUTF().split("\n");
                        Organization organization = null;
                        List<Organization> organizationsList = new LinkedList<>();

                        for (int j = 1; j < dataArray.length; j++) {
                            String data = dataArray[j];
                            if (!data.startsWith("2")) {
                                int position = data.indexOf(",");
                                String name = data.substring(0, position);
                                String url = data.substring(position + 2);
                                organization = new Organization(name, url);
                            }
                            if (data.startsWith("2")) {
                                int position = data.indexOf(" - ");
                                LocalDate startDate = getDate(data, 0, position);
                                LocalDate endDate = getDate(data, position + 3, data.indexOf(","));
                                position = data.indexOf(endDate.toString()) + endDate.toString().length() + 2;
                                String title = data.substring(position, data.indexOf(",", position + 3));
                                String description = data.substring(data.indexOf(title) + title.length() + 2);
                                organization.addOrganizationInfo(startDate, endDate, title, description);

                                if (!(new HashSet<>(organizationsList).contains(organization))) {
                                    organizationsList.add(organization);
                                }
                            }
                        }
                        as = new OrganizationSection(organizationsList);
                }
                resume.addSection(SectionType.valueOf(section), as);
            }
            return resume;
        }
    }

    private LocalDate getDate(String data, int from, int to) {
        return LocalDate.parse(data.substring(from, to));
    }
}