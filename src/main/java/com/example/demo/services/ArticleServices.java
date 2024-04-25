package com.example.demo.services;

import com.example.demo.entity.Article;
import com.example.demo.util.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleServices implements IService<Article> {

    private Connection connection;

    public ArticleServices() {
        this.connection = MyDb.getInstance().getCnx();
        if (connection == null) {
            throw new RuntimeException("Database connection could not be established.");
        }
    }

    @Override
    public void add(Article entity) throws SQLException {
        String sql = "INSERT INTO articles (image, title, categorie, content, createdat, metacontent) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getImage());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getCategorie());
            preparedStatement.setString(4, entity.getContent());
            preparedStatement.setDate(5, new Date(entity.getCreatedat().getTime()));
            preparedStatement.setString(6, entity.getMetacontent());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Article entity) throws SQLException {
        String sql = "UPDATE articles SET image = ?, title = ?, categorie = ?, content = ?, " +
                "createdat = ?, metacontent = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getImage());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getCategorie());
            preparedStatement.setString(4, entity.getContent());
            preparedStatement.setDate(5, new Date(entity.getCreatedat().getTime()));
            preparedStatement.setString(6, entity.getMetacontent());
            preparedStatement.setInt(7, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM articles WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Article> getAll() throws SQLException {
        String sql = "SELECT * FROM articles";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Article> entities = new ArrayList<>();
            while (resultSet.next()) {
                Article entity = new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("image"),
                        resultSet.getString("title"),
                        resultSet.getString("categorie"),
                        resultSet.getString("content"),
                        resultSet.getDate("createdat"),
                        resultSet.getString("metacontent")
                );
                entities.add(entity);
            }
            return entities;
        }
    }

    @Override
    public Article getById(int id) throws SQLException {
        String sql = "SELECT * FROM your_table_name WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("image"),
                        resultSet.getString("title"),
                        resultSet.getString("categorie"),
                        resultSet.getString("content"),
                        resultSet.getDate("createdat"),
                        resultSet.getString("metacontent")
                );
            } else {
                return null; // No matching record found
            }
        }
    }
}
