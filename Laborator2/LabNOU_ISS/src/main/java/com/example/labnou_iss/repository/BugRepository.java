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
}
