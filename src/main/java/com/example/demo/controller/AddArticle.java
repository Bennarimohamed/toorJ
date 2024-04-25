package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.services.ArticleServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddArticle {

    @FXML
    private TextField categoryTF;

    @FXML
    private Label contentTF;

    @FXML
    private DatePicker createdatTF;

    @FXML
    private TextField imgeTF;

    @FXML
    private Label metacontentTF;

    @FXML
    private TextField titleTF;

    private ArticleServices articleService;

    @FXML
    public void initialize() {
        try {
            articleService = new ArticleServices();
        } catch (RuntimeException e) {
            System.err.println("Error initializing ArticleServices: " + e.getMessage());
        }
    }

    @FXML
    void addarticleB(ActionEvent event) {
        // Perform validation here if needed

        try {
            Article article = new Article();
            article.setTitle(titleTF.getText());
            article.setCategorie(categoryTF.getText());
            article.setContent(contentTF.getText()); // Assuming contentTF is a Label
            article.setMetacontent(metacontentTF.getText()); // Assuming metacontentTF is a Label
            article.setCreatedat(java.sql.Date.valueOf(createdatTF.getValue())); // Assuming createdatTF is a DatePicker
            article.setImage(imgeTF.getText()); // Assuming imgeTF is a TextField

            // Add the new article
            articleService.add(article);

            // You can add additional logic here such as showing a confirmation message

        } catch (SQLException e) {
            System.err.println("Error adding article: " + e.getMessage());
        }
    }
}
