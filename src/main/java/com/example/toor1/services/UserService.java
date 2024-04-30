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
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Interprétez la valeur du champ "is_blocked" comme un booléen
                boolean isBlocked = resultSet.getInt("is_blocked") == 1; // Si 1, c'est bloqué

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("photo"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("birthdate"),
                        resultSet.getString("address"),
                        resultSet.getString("country"),
                        isBlocked // Utilisez le booléen interprété
                );
            }
            return null;
        }
    }

    public List<User> getUsersByName(String name) throws SQLException {
        String sql = "SELECT * FROM user WHERE name LIKE ? OR lastname LIKE ?"; // Use partial matching with LIKE
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%"); // Add wildcard for partial matching
            preparedStatement.setString(2, "%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
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
            return users; // Return the list of users matching the search query
        }
    }
    // Méthode pour bloquer un utilisateur
    public void blockUser(int userId) throws SQLException {
        String sql = "UPDATE user SET is_blocked = TRUE WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour débloquer un utilisateur
    public void unblockUser(int userId) throws SQLException {
        String sql = "UPDATE user SET is_blocked = FALSE WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour vérifier si un utilisateur est bloqué
    public boolean isUserBlocked(int userId) throws SQLException {
        String sql = "SELECT is_blocked FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("is_blocked");
            }
            return false; // Par défaut, l'utilisateur n'est pas bloqué
        }
    }
}
