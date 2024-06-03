package com.example.labnou_iss.repository;

import com.example.labnou_iss.domain.Bug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class BugRepository {

    private JdbcUtils dbUtils;
    private Connection connection;
    private static final Logger logger = LogManager.getLogger();

    public BugRepository(Properties props){
        logger.info("Initializing BugRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        connection = dbUtils.getConnection();
    }

    public void save(Bug bug) {
        logger.traceEntry("saving bug {}", bug);
        String sql = "INSERT INTO bug(id, name, description, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, bug.getId());
            statement.setString(2, bug.getName());
            statement.setString(3, bug.getDescription());
            statement.setString(4, bug.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
    }

    public Iterable<Bug> findAll() {
        logger.traceEntry("finding all bugs");
        List<Bug> bugs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM bug")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                bugs.add(new Bug(id, name, description, status));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(bugs);
        return bugs;
    }

    public Bug findById(UUID id) {
        logger.traceEntry("finding bug by id: {}", id);
        Bug bug = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM bug WHERE id = ?")) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                bug = new Bug(id, name, description, status);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(bug);
        return bug;
    }

    public void delete(UUID id) {
        logger.traceEntry("deleting bug with id: {}", id);
        String sql = "DELETE FROM bug WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
    }

    public UUID getBugId(Bug bug) {
        logger.traceEntry("getting id for bug: {}", bug);
        UUID id = null;
        String sql = "SELECT id FROM bug WHERE name = ? AND description = ? AND status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bug.getName());
            statement.setString(2, bug.getDescription());
            statement.setString(3, bug.getStatus());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = UUID.fromString(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(id);
        return id;
    }

    public Bug findBugById(UUID bugUUID) {
        logger.traceEntry("finding bug by id: {}", bugUUID);
        Bug bug = null;
        String sql = "SELECT * FROM bug WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, bugUUID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                bug = new Bug(id, name, description, status);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(bug);
        return bug;
    }

    public void updateBug(Bug bug) {
        logger.traceEntry("updating bug {}", bug);
        String sql = "UPDATE bug SET name = ?, description = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bug.getName());
            statement.setString(2, bug.getDescription());
            statement.setString(3, bug.getStatus());
            statement.setObject(4, bug.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
    }
}
