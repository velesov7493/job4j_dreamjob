package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlPostStore implements PostStore {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlPostStore.class.getName());
    private static final PsqlPostStore INSTANCE = new PsqlPostStore();

    private final BasicDataSource pool;

    private PsqlPostStore() {
        pool = AppSettings.getConnectionPool();
    }

    public static PostStore getInstance() {
        return INSTANCE;
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
                    Post entry = new Post(
                        it.getInt("id"),
                        it.getString("pName")
                    );
                    entry.setDescription(it.getString("pDescription"));
                    entry.setCreated(it.getDate("pCreated"));
                    result.add(entry);
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public Collection<Post> findAllCreatedToday() {
        List<Post> result = new ArrayList<>();
        String query =
                "SELECT * FROM tz_posts "
                + "WHERE pCreated BETWEEN (current_date - interval '1 day') AND current_date;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Post entry = new Post(
                            it.getInt("id"),
                            it.getString("pName")
                    );
                    entry.setDescription(it.getString("pDescription"));
                    entry.setCreated(it.getDate("pCreated"));
                    result.add(entry);
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
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
                    result.setDescription(it.getString("pDescription"));
                    result.setCreated(it.getDate("pCreated"));
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
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
                LOG.error("Ошибка при выполнении запроса: ", ex);
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
                LOG.error("Ошибка при выполнении запроса: ", ex);
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
                LOG.error("Ошибка при выполнении запроса: ", ex);
            }
        }
    }
}
