package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.ImageStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@MultipartConfig(location = "/var/tmp/tomcat", maxFileSize = 16777216L, maxRequestSize = 33554432L)
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

        CandidateStore store = CandidateStore.getInstance();
        ImageStore imgStore = ImageStore.getInstance();
        String sid = new String(
                req.getPart("nCandidateId").getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        String name = new String(
                req.getPart("nName").getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        String position = new String(
                req.getPart("nPosition").getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        Candidate c = new Candidate(
                Integer.parseInt(sid),
                name,
                position
        );
        store.save(c);
        imgStore.saveFromStream(c.getId(), req.getPart("nPhoto").getInputStream());
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
