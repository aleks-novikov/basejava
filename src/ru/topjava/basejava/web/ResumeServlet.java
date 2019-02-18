package ru.topjava.basejava.web;

import ru.topjava.basejava.model.*;
import ru.topjava.basejava.storage.Storage;
import ru.topjava.basejava.util.Config;
import ru.topjava.basejava.util.DateUtil;
import ru.topjava.basejava.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullname = request.getParameter("fullName");
        Resume resume;
        boolean resumeIsNew = false;

        //если uuid пусто, значит резюме ещё не создано
        if (uuid == null) {
            resume = new Resume(UUID.randomUUID().toString(), fullname);
            storage.save(resume);
            resumeIsNew = true;
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullname);
        }

        //обработка поступившей информации о контактах
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }

        //обработка поступившей информации о секциях
        for (SectionType type : SectionType.values()) {
            String sectionData = request.getParameter(type.name());
            String[] organizationsNames = request.getParameterValues(type.name());

            if (HtmlUtil.isEmpty(sectionData) && organizationsNames.length < 2) {
                resume.getSections().remove(type);
            } else
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(type, new TextSection(sectionData));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        resume.setSection(type, new ListSection(Arrays.asList(
                                sectionData.replace("\r", "").split("\\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < organizationsNames.length; i++) {
                            String name = organizationsNames[i];
                            //если имя организации пустое, значит данной позиции нет, и не берём её данные
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                //получение значения counter.index, заданного в файле edit.jsp
                                //индекс необходим для корректного сопоставления данных
                                String prefix = type.name() + (resumeIsNew ? "" : i);

                                String[] startDates = request.getParameterValues(prefix + "startDate");
                                String[] endDates = request.getParameterValues(prefix + "endDate");
                                String[] titles = request.getParameterValues(prefix + "title");
                                String[] descriptions = request.getParameterValues(prefix + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Organization.Position(
                                                DateUtil.parse(startDates[j]),
                                                DateUtil.parse(endDates[j]),
                                                titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name,
                                        urls == null ? "" : urls[i]),
                                        positions));
                            }
                        }
                        resume.setSection(type, new OrganizationSection(organizations));
                        break;
                }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    //запрос к сервлету с параметром
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            //перенаправление на jsp-файл и передача ему запроса и ответа
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume = (uuid != null) ? storage.get(uuid) : null;
        String pageName;
        switch (action) {
            case "add":
                pageName = "add.jsp";
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                pageName = "view.jsp";
                break;
            case "edit":
                pageName = "edit.jsp";
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    OrganizationSection section = (OrganizationSection) resume.getSection(type);
                    List<Organization> emptyFirstOrganization = new ArrayList<>();
                    emptyFirstOrganization.add(Organization.EMPTY);
                    //если в резюме нет секций, остаётся 1 пустая секция для заполнения
                    //иначе добавление секций резюме в список
                    if (section != null) {
                        //создание пустой ячейки для вставки позиции
                        for (Organization organization : section.getOrganisations()) {
                            List<Organization.Position> emptyFirstPosition = new ArrayList<>();
                            //первая позиция пустая
                            emptyFirstPosition.add(Organization.Position.EMPTY);
                            //добавление всех остальных позиций
                            emptyFirstPosition.addAll(organization.getPositions());
                            emptyFirstOrganization.add(new Organization(organization.getHomePage(), emptyFirstPosition));
                        }
                    }
                    //"перерисовка" резюме
                    resume.setSection(type, new OrganizationSection(emptyFirstOrganization));
                }
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal!");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("/WEB-INF/jsp/" + pageName).forward(request, response);
    }
}