package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users_table (" +
                        "user_id INT(10) PRIMARY KEY AUTO_INCREMENT, " +
                        "user_name VARCHAR(50), " +
                        "user_lastname VARCHAR(50), " +
                        "user_age INT)")) {
            Objects.requireNonNull(connection).setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(
                "DROP TABLE IF EXISTS users_table")) {
            Objects.requireNonNull(connection).setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users_table (user_name, user_lastname, user_age) " +
                        "VALUES (?, ?, ?);")) {
            Objects.requireNonNull(connection).setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users_table WHERE user_id = " + id)) {
            Objects.requireNonNull(connection).setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> results = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users_table")) {
            Objects.requireNonNull(connection).setAutoCommit(false);
            resultSet = statement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                String name = resultSet.getString("user_name");
                String lastName = resultSet.getString("user_lastname");
                Byte age = (byte) resultSet.getInt("user_age");
                results.add(new User(name, lastName, age));
            }
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return results;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(
                "TRUNCATE TABLE users_table")) {
            Objects.requireNonNull(connection).setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

}
