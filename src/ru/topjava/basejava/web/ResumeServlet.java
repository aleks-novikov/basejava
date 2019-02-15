package ru.topjava.basejava.web;

import ru.topjava.basejava.model.*;
import ru.topjava.basejava.storage.Storage;
import ru.topjava.basejava.util.Config;
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
        Resume resume = storage.get(uuid);
        resume.setFullName(fullname);

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
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        resume.setSection(type, new ListSection(Arrays.asList(value.split("\\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
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
        Resume resume;
        switch (action) {
            case "add":
//                String fullName = request.getParameter("fullName");
                String fullName = "ФИО";
                resume = new Resume(UUID.randomUUID().toString(), fullName);
                storage.save(resume);
                break;
            case "delete":
                storage.delete(uuid);
                //перенаправление на начальную страницу приложения с помощью запроса к серверу с параметром "resume"
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
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

        //вызов того или иного jsp-файла согласно выбранному действию
        String pageName = null;
        switch (action) {
            case "add":
                pageName = "add.jsp";
                break;
            case "edit":
                pageName = "edit.jsp";
                break;
            case "view":
                pageName = "view.jsp";
        }
        request.getRequestDispatcher("/WEB-INF/jsp/" + pageName).forward(request, response);
    }
}