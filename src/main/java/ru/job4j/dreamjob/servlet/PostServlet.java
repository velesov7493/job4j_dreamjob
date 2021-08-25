package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    private void editPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        Post p = new Post(0, "");
        if (id != 0) {
            p = PostStore.getInstance().getById(id);
        }
        req.setAttribute("post", p);
        req.getRequestDispatcher("post/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            editPost(req, resp);
            return;
        }
        req.setAttribute("posts", PostStore.getInstance().findAll());
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        PostStore store = PostStore.getInstance();
        store.save(
                new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("nPosition")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
