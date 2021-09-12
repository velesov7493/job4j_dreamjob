package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlCityStore implements CityStore {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlCityStore.class.getName());
    private static final PsqlCityStore INSTANCE = new PsqlCityStore();

    private final BasicDataSource pool;

    private PsqlCityStore() {
        pool = AppSettings.getConnectionPool();
    }

    public static CityStore getInstance() {
        return INSTANCE;
    }

    private void create(City value) {
        String query = "INSERT INTO tz_cities (ctName) VALUES (?);";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setString(1, value.getName());
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

    private void update(City value) {
        String query = "UPDATE tz_cities SET ctName=? WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setString(1, value.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                LOG.error("Ошибка при выполнении запроса: ", ex);
            }
        }
    }

    @Override
    public Collection<City> findAll() {
        List<City> result = new ArrayList<>();
        String query = "SELECT * FROM tz_cities ORDER BY ctName;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    City c = new City(it.getString("ctName"));
                    c.setId(it.getInt("id"));
                    result.add(c);
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public City getById(int id) {
        City result = null;
        String query = "SELECT * FROM tz_cities WHERE id=?;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new City(it.getString("ctName"));
                    result.setId(it.getInt("id"));
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public void save(City value) {
        if (value.getId() <= 0) {
            create(value);
        } else {
            update(value);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM tz_cities WHERE id=?";
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
