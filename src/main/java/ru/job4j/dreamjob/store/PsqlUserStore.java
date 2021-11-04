package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.Security;
import ru.job4j.dreamjob.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlUserStore implements UserStore {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlUserStore.class.getName());
    private static final PsqlUserStore INSTANCE = new PsqlUserStore();

    private final BasicDataSource pool;

    private PsqlUserStore() {
        pool = AppSettings.getConnectionPool();
    }

    public static PsqlUserStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<User> findAll() {
        List<User> result = new ArrayList<>();
        String query = "SELECT * FROM tz_users;";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement ps = cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    User u = new User();
                    u.setId(it.getInt("id"));
                    u.setName(it.getString("uName"));
                    u.setEmail(it.getString("uMail"));
                    u.setPassword("uPassword");
                    result.add(u);
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public User getById(int id) {
        User result = null;
        String query = "SELECT * FROM tz_users WHERE id=?;";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement ps = cn.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new User();
                    result.setId(it.getInt("id"));
                    result.setName(it.getString("uName"));
                    result.setEmail(it.getString("uMail"));
                    result.setPassword("uPassword");
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public User login(String email, String password) {
        User result = null;
        String query = "SELECT * FROM tz_users WHERE uMail=? AND uPassword=?;";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement ps = cn.prepareStatement(query)
        ) {
            ps.setString(1, email);
            ps.setString(2, Security.getSHA1(password));
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new User();
                    result.setId(it.getInt("id"));
                    result.setName(it.getString("uName"));
                    result.setEmail(it.getString("uMail"));
                    result.setPassword("uPassword");
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    private boolean create(User value) {
        boolean result = false;
        String query = "INSERT INTO tz_users (uName, uMail, uPassword) VALUES (?, ?, ?);";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setString(1, value.getName());
            st.setString(2, value.getEmail());
            st.setString(3, Security.getSHA1(value.getPassword()));
            st.execute();
            ResultSet keys = st.getGeneratedKeys();
            if (keys.next()) {
                value.setId(keys.getInt(1));
                result = true;
            }
            keys.close();
        } catch (SQLException ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    private boolean update(User value) {
        boolean result = false;
        String query = "UPDATE tz_users SET uName=?, uMail=?, uPassword=? WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setString(1, value.getName());
            st.setString(2, value.getEmail());
            st.setString(3, Security.getSHA1(value.getPassword()));
            st.setInt(4, value.getId());
            result = st.executeUpdate() > 0;
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                LOG.error("Ошибка при выполнении запроса: ", ex);
            }
        }
        return result;
    }

    @Override
    public boolean save(User value) {
        boolean result;
        if (value.getId() <= 0) {
            result = create(value);
        } else {
            result = update(value);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        String query = "DELETE FROM tz_users WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setInt(1, id);
            result = st.executeUpdate() > 0;
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                LOG.error("Ошибка при выполнении запроса: ", ex);
            }
        }
        return result;
    }
}
