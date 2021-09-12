package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.PsqlCandidateStore;
import ru.job4j.dreamjob.store.PsqlPostStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        CandidateStore cStore = PsqlCandidateStore.getInstance();
        PostStore pStore = PsqlPostStore.getInstance();
        req.setAttribute("isServlet", true);
        req.setAttribute("candidates", cStore.findAllCreatedToday());
        req.setAttribute("posts", pStore.findAllCreatedToday());
        req.getRequestDispatcher("views/index.jsp").forward(req, resp);
    }
}
