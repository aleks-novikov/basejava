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
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    //запрос к сервлету с параметром
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");  //задание типа ответа (код html) и кодировки для браузера
        response.getWriter().write(printAllResumes());
    }

    private String printAllResumes() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html lang=ru>\n<body>\n<table border='1' cellpadding='5'>\n");

        for (Resume resume : storage.getAllSorted()) {
            sb.append("<tr>\n<th colspan = '2'><h2>").append(resume.getFullName()).append("</h2></th>\n</tr>");

            //добавление контактов
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                sb.append("<tr>\n<td>").append(contact.getKey().getTitle()).append("</td>\n");
                sb.append("<td>").append(contact.getValue()).append("</td>\n</tr>\n");
            }

            //добавление секций
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                SectionType type = section.getKey();
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        sb.append("<tr>\n<td>").append(section.getKey().getTitle()).append("</td>\n");
                        sb.append("<td>").append(section.getValue()).append("</td>\n</tr>\n");
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        sb.append("<tr>\n<td>").append(section.getKey().getTitle()).append("</td>\n");
                        sb.append("<td>\n<ul>");

                        ListSection list = (ListSection) section.getValue();
                        for (String data : list.getItems()) {
                            sb.append("<li>").append(data).append("</li>");
                        }
                        sb.append("</ul>\n</td>\n</tr>");
                }
            }
        }
        sb.append("</table>\n</body>\n</html>");
        return sb.toString();
    }
}