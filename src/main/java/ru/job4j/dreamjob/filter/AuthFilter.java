package ru.job4j.dreamjob.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (
                uri.endsWith("auth.do")
                        || uri.endsWith("index.do")
                        || uri.endsWith("register.do")
        ) {
            filterChain.doFilter(req, resp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth.do");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
