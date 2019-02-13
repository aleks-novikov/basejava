package ru.topjava.basejava.web;

import ru.topjava.basejava.model.*;
import ru.topjava.basejava.storage.Storage;
import ru.topjava.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

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
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        //обработка поступившей информации о секциях
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                value = value.replaceAll("\\s {2,}", "");
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        String[] values = value.replace("\n", "").split ("\r");
                        resume.addSection(type, new ListSection(Arrays.asList(values)));
                }
            } else {
                resume.getSections().remove(type);
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
            case "delete":
                storage.delete(uuid);
                //перенаправление на начальную страницу приложения с помощью запроса к серверу с параметром "resume"
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal!");
        }
        request.setAttribute("resume", resume);

        //вызов того или иного jsp-файла согласно выбранному действию
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}