package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        CandidateStore store = CandidateStore.getInstance();
        store.save(
            new Candidate(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("nName"),
                req.getParameter("nPosition")
            ));
        resp.sendRedirect(req.getContextPath() + "/candidates.jsp");
    }
}
