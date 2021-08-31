package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dreamjob.AppSettings;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlCandidateStore implements CandidateStore {

    private final BasicDataSource pool;

    public PsqlCandidateStore() {
        pool = AppSettings.getConnectionPool();
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
                    result.add(new Candidate(
                            it.getInt("id"),
                            it.getString("cName"),
                            it.getString("cPosition")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void create(Candidate value) {
        String query = "INSERT INTO tz_candidates (cName, cPosition) VALUES (?, ?);";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setString(1, value.getName());
            st.setString(2, value.getPosition());
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

    private void update(Candidate value) {
        String query = "UPDATE tz_candidates SET cName=?, cPosition=? WHERE id=?";
        try (
                Connection cn = pool.getConnection();
                PreparedStatement st = cn.prepareStatement(query)
        ) {
            st.setString(1, value.getName());
            st.setString(2, value.getPosition());
            st.setInt(3, value.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                ex.printStackTrace();
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
                ex.printStackTrace();
            }
        }
    }
}