package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    private void editCandidate(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        Candidate c = new Candidate(0, "", "");
        if (id != 0) {
            c = CandidateStore.getInstance().getById(id);
        }
        req.setAttribute("candidate", c);
        req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (req.getParameter("id") != null) {
            editCandidate(req, resp);
            return;
        }
        req.setAttribute("candidates", CandidateStore.getInstance().findAll());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

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
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
