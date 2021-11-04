package ru.job4j.dreamjob.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityStore;
import ru.job4j.dreamjob.store.PsqlCityStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class CityServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CityStore store = PsqlCityStore.getInstance();
        Collection<City> cities = store.findAll();
        resp.setContentType("application/json; charset=utf-8");
        String json = GSON.toJson(cities);
        OutputStream out = resp.getOutputStream();
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
