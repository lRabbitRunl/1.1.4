package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection SQL_connection;

    public UserDaoJDBCImpl() {
        SQL_connection = Util.getConnection();
    }

    public void createUsersTable() {

        String sql = "create table IF NOT EXISTS userTable" +
                "(id BIGINT primary key auto_increment, " +
                "name varchar(100), " +
                "lastname varchar(100), " +
                "age tinyint);";
        try (Statement statement = SQL_connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sql = "drop table IF EXISTS userTable;";
        try (Statement statement = SQL_connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO userTable (name,lastname,age) VALUES (?,?,?);";
        try (PreparedStatement preparedStatement = SQL_connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM userTable WHERE ID = ?;";
        try (PreparedStatement preparedStatement = SQL_connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id,name,lastname,age from userTable";
        try (Statement statement = SQL_connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(      resultSet.getLong("id"));
                user.setName(    resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(     resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE userTable;";
        try (Statement statement = SQL_connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
