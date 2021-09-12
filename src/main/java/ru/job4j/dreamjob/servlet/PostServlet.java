package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.PsqlPostStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    private void deletePost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        PostStore store = PsqlPostStore.getInstance();
        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        if (id != 0) {
            store.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

    private void editPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        PostStore store = PsqlPostStore.getInstance();
        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        Post p = new Post(0, "");
        if (id != 0) {
            p = store.getById(id);
        }
        req.setAttribute("post", p);
        req.getRequestDispatcher("views/post/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (req.getParameter("id") != null) {
            String del = req.getParameter("delete");
            if (del != null) {
                deletePost(req, resp);
                return;
            }
            editPost(req, resp);
            return;
        }
        PostStore store = PsqlPostStore.getInstance();
        req.setAttribute("posts", store.findAll());
        req.getRequestDispatcher("views/post/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        PostStore store = PsqlPostStore.getInstance();
        Post p = new Post(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("nPosition")
        );
        p.setDescription(req.getParameter("nDescription"));
        store.save(p);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
