package ru.job4j.dreamjob.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "OPTIONS, HEAD, GET, POST");
        if ("OPTIONS".equals(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
        filterChain.doFilter(req, resp);
    }
}
