package ru.job4j.dreamjob.servlet;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.MemPostStore;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.PsqlPostStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlPostStore.class)
public class PostServletTest {

    @Test
    @Ignore
    public void whenCreatePost() throws IOException, ServletException {
        PostStore store = MemPostStore.getInstance();
        PowerMockito.mockStatic(PsqlPostStore.class);
        PowerMockito.when(PsqlPostStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Mockito.when(req.getParameter("id")).thenReturn("0");
        Mockito.when(req.getParameter("nPosition")).thenReturn("Java middle");
        Mockito.when(req.getParameter("nDescription")).thenReturn("Java middle job");
        new PostServlet().doPost(req, resp);
        Post result = store.findAll().iterator().next();
        Assert.assertThat(result.getName(), Is.is("Java middle"));
        Assert.assertThat(result.getDescription(), Is.is("Java middle job"));
    }


}