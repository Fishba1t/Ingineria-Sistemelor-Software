package com.example.labnou_iss.repository;

import com.example.labnou_iss.domain.Angajat;
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

public class AngajatRepository  {

    private JdbcUtils dbUtils;

    private Connection connection;

    private static final Logger logger = LogManager.getLogger();

    public AngajatRepository(Properties props){
        logger.info("Initializing ArbitruRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        connection = dbUtils.getConnection();
    }


    public void save(Angajat angajat) {
        String sql = "INSERT INTO angajat(id, username, password, position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Generate UUID for the id column
            UUID id = UUID.randomUUID();
            statement.setObject(1, id);
            statement.setString(2, angajat.getUsername());
            statement.setString(3, angajat.getPassword());
            statement.setString(4, angajat.getPosition());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("A new angajat was inserted successfully.");
            }
        } catch (SQLException e) {
            logger.error("Error while inserting angajat: ", e);
        }
    }


    public Iterable<Angajat> findAll() {
        logger.traceEntry("finding all angajats");
        List<Angajat> angajats = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM angajat")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String position = resultSet.getString("position");
                angajats.add(new Angajat(id, username, password, position));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(angajats);
        return angajats;
    }

    public Angajat findOneByUsername(String username) {
        logger.traceEntry("finding angajat by username: {}", username);
        Angajat angajat = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM angajat WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String password = resultSet.getString("password");
                String position = resultSet.getString("position");
                angajat = new Angajat(id, username, password, position);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(angajat);
        return angajat;
    }

    public Angajat findAngajatByUsernamePassword(String username, String password) {
        logger.traceEntry("finding angajat by username and password {}", username);
        Angajat angajat = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM angajat WHERE username=? AND password=?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String position = resultSet.getString("position");
                angajat = new Angajat(id, username, password, position);
            }
        } catch (SQLException e) {
            return null;
        }
        logger.traceExit(angajat);
        return angajat;
    }




}
