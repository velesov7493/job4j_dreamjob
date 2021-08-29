package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Image;
import ru.job4j.dreamjob.store.ImageStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String sid = req.getParameter("id");
        if (sid == null) {
            resp.setStatus(404);
            return;
        }
        String download = req.getParameter("download");
        String delete = req.getParameter("delete");
        int id = Integer.parseInt(sid);
        ImageStore store = ImageStore.getInstance();
        if (delete != null && id != 0) {
            store.delete(id);
            resp.sendRedirect(req.getContextPath() + "/candidates.do?id=" + sid);
            return;
        }
        Image img = store.load(id);
        if (img.isEmpty()) {
            resp.setStatus(404);
            return;
        }
        if (download == null) {
            resp.setHeader("Content-Type", "image/*");
            resp.getOutputStream().write(img.getContent());
        } else {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"image\"");
            resp.getOutputStream().write(img.getContent());
        }
    }
}