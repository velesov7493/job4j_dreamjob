package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@MultipartConfig(maxFileSize = 16777216L, maxRequestSize = 33554432L)
public class CandidateServlet extends HttpServlet {

    private void editCandidate(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        CandidateStore store = PsqlCandidateStore.getInstance();
        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        Candidate c = new Candidate(0, "", "");
        if (id != 0) {
            c = store.getById(id);
        }
        req.setAttribute("candidate", c);
        req.getRequestDispatcher("views/candidate/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (req.getParameter("id") != null) {
            editCandidate(req, resp);
            return;
        }
        CandidateStore store = PsqlCandidateStore.getInstance();
        req.setAttribute("candidates", store.findAll());
        req.getRequestDispatcher("views/candidate/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        CandidateStore store = PsqlCandidateStore.getInstance();
        ImageStore imgStore = FilesImageStore.getInstance();
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
        Part photo = req.getPart("nPhoto");
        if (photo != null && photo.getSize() > 0) {
            imgStore.saveFromStream(c.getId(), photo.getInputStream());
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
