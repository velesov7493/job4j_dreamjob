package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlPostStore implements PostStore {

    private final BasicDataSource pool;

    public PsqlPostStore() {
        pool = AppSettings.getConnectionPool();
    }

    @Override
    public Collection<Post> findAll() {
        List<Post> result = new ArrayList<>();
        String query = "SELECT * FROM tz_posts;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(new Post(
                        it.getInt("id"),
                        it.getString("pName")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post getById(int id) {
        Post result = null;
        String query = "SELECT * FROM tz_posts WHERE id=?;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new Post(
                        it.getInt("id"),
                        it.getString("pName")
                    );
                    result.setCreated(it.getDate("pCreated"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void create(Post value) {
        String query = "INSERT INTO tz_posts (pName, pDescription) VALUES (?, ?);";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS
                )
        ) {
            st.setString(1, value.getName());
            st.setString(2, value.getDescription());
            st.execute();
            ResultSet keys = st.getGeneratedKeys();
            if (keys.next()) {
                value.setId(keys.getInt(1));
            }
            keys.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                ex.printStackTrace();
            }
        }
    }

    private void update(Post value) {
        String query = "UPDATE tz_posts SET pName=?, pDescription=? WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setString(1, value.getName());
            st.setString(2, value.getDescription());
            st.setInt(3, value.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void save(Post value) {
        if (value.getId() <= 0) {
            create(value);
        } else {
            update(value);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM tz_posts WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                ex.printStackTrace();
            }
        }
    }
}
