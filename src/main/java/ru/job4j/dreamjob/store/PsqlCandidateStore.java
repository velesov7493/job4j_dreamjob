package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlCandidateStore implements CandidateStore {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlCandidateStore.class.getName());
    private static final PsqlCandidateStore INSTANCE = new PsqlCandidateStore();

    private final BasicDataSource pool;

    private PsqlCandidateStore() {
        pool = AppSettings.getConnectionPool();
    }

    public static CandidateStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> result = new ArrayList<>();
        String query = "SELECT * FROM tz_candidates;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Candidate c = new Candidate(
                        it.getInt("id"),
                        it.getString("cName"),
                        it.getString("cPosition")
                    );
                    c.setCreated(it.getDate("cCreated"));
                    c.setCityId(it.getInt("id_city"));
                    result.add(c);
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public Collection<Candidate> findAllCreatedToday() {
        List<Candidate> result = new ArrayList<>();
        String query =
                "SELECT * FROM tz_candidates "
                + "WHERE cCreated BETWEEN (current_date - interval '1 day') AND current_date;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Candidate c = new Candidate(
                            it.getInt("id"),
                            it.getString("cName"),
                            it.getString("cPosition")
                    );
                    c.setCreated(it.getDate("cCreated"));
                    c.setCityId(it.getInt("id_city"));
                    result.add(c);
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    @Override
    public Candidate getById(int id) {
        Candidate result = null;
        String query = "SELECT * FROM tz_candidates WHERE id=?;";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new Candidate(
                            it.getInt("id"),
                            it.getString("cName"),
                            it.getString("cPosition")
                    );
                    result.setCityId(it.getInt("id_city"));
                    result.setCreated(it.getDate("cCreated"));
                }
            }
        } catch (Exception ex) {
            LOG.error("Ошибка при выполнении запроса: ", ex);
        }
        return result;
    }

    private void create(Candidate value) {
        String query = "INSERT INTO tz_candidates (id_city, cName, cPosition) VALUES (?, ?, ?);";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setObject(1, value.getCityId(), Types.INTEGER);
            st.setString(2, value.getName());
            st.setString(3, value.getPosition());
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

    private void update(Candidate value) {
        String query = "UPDATE tz_candidates SET id_city=?, cName=?, cPosition=? WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setObject(1, value.getCityId(), Types.INTEGER);
            st.setString(2, value.getName());
            st.setString(3, value.getPosition());
            st.setInt(4, value.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                LOG.error("Ошибка при выполнении запроса: ", ex);
            }
        }
    }

    @Override
    public void save(Candidate value) {
        if (value.getId() <= 0) {
            create(value);
        } else {
            update(value);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM tz_candidates WHERE id=?";
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