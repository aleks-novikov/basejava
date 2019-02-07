package ru.topjava.basejava.web;

import ru.topjava.basejava.storage.Storage;
import ru.topjava.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("resumes", storage.getAllSorted());
        //перенаправление на jsp-файл и передача ему запроса и ответа
        request.getRequestDispatcher("./WEB-INF/jsp/list.jsp").forward(request, response);
    }
}