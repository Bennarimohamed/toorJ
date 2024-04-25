package com.example.toor1.services;

import com.example.toor1.entity.User;
import com.example.toor1.util.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private Connection connection;

    public UserService() {
        this.connection = MyDb.getInstance().getCnx();
        if (connection == null) {
            throw new RuntimeException("Database connection could not be established.");
        }
    }

    @Override
    public void add(User user) throws SQLException {
        String sql = "INSERT INTO user (email, roles, password, photo, name, lastname, birthdate, address, country) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getRoles());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhoto());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getLastname());

            if (user.getBirthdate() != null) {
                preparedStatement.setDate(7, new java.sql.Date(user.getBirthdate().getTime()));
            } else {
                preparedStatement.setNull(7, java.sql.Types.DATE);
            }

            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setString(9, user.getCountry());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET email = ?, roles = ?, password = ?, photo = ?, name = ?, lastname = ?, " +
                "birthdate = ?, address = ?, country = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getRoles());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhoto());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getLastname());

            if (user.getBirthdate() != null) {
                preparedStatement.setDate(7, new java.sql.Date(user.getBirthdate().getTime()));
            } else {
                preparedStatement.setNull(7, java.sql.Types.DATE);
            }

            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setString(9, user.getCountry());
            preparedStatement.setInt(10, user.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM user";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("photo"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("birthdate"), // Handle null-check here
                        resultSet.getString("address"),
                        resultSet.getString("country")
                );
                users.add(user);
            }
            return users;
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("photo"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("birthdate"), // Handle null-check
                        resultSet.getString("address"),
                        resultSet.getString("country")
                );
            } else {
                return null; // No matching record found
            }
        }
    }

    public User getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email); // Set email as parameter
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute query

            if (resultSet.next()) { // If a record is found
                // Create and return a User object from the ResultSet
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("photo"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("birthdate"), // Handle null-check
                        resultSet.getString("address"),
                        resultSet.getString("country")
                );
            } else {
                return null; // No user with this email found
            }
        } catch (SQLException e) {
            System.err.println("Error in getByEmail: " + e.getMessage());
            throw e; // Rethrow exception
        }
    }
}
