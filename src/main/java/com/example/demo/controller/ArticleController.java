package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.services.ArticleServices; // Assuming ArticleService is the service class for articles
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ArticleController {

    @FXML
    private Button addArticleHomeButton;

    @FXML
    private TableColumn<ArticleController, Integer> idTB;

    @FXML
    private TableColumn<ArticleController, String> titleTB;

    @FXML
    private TableColumn<ArticleController, String> categoryTB;

    @FXML
    private TableColumn<ArticleController, String> contentTB;

    @FXML
    private TableColumn<ArticleController, String> metacontentTB;

    @FXML
    private TableColumn<ArticleController, String> dateTB;

    @FXML
    private TableColumn<ArticleController, String> photoTB;

    @FXML
    private TableView<Article> userTableView;

    @FXML
    void addArticleB(ActionEvent event) {
        try {
            // Redirect to Register.fxml when the register button is clicked
            Parent root = FXMLLoader.load(getClass().getResource("/AddArticle.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show
                    ();
        } catch (Exception e) {
            System.err.println("Error loading AddArticle.fxml: " + e.getMessage());
        }
    }

    private void showAlert(String error, String s) {
    }

    @FXML
    void deleteArticleB(ActionEvent event) {
        // Add logic to handle deleting an article
    }

    @FXML
    void editArticleB(ActionEvent event) {
        // Add logic to handle editing an article
    }

    @FXML
    void showArticleB(ActionEvent event) {
        // Add logic to handle showing detailed view of an article
    }

    @FXML
    public void initialize() {  // Initialization method
        // Configure the TableColumns with PropertyValueFactory
        idTB.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleTB.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryTB.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        contentTB.setCellValueFactory(new PropertyValueFactory<>("content"));
        metacontentTB.setCellValueFactory(new PropertyValueFactory<>("metacontent"));
        dateTB.setCellValueFactory(new PropertyValueFactory<>("createdat"));
        photoTB.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Initialize the TableView with articles
        initializeArticleTableView();
    }


    private void initializeArticleTableView() {
        ArticleServices articleService = new ArticleServices();
        try {
            // Fetch the list of all articles from the database
            ObservableList<Article> articles = FXCollections.observableArrayList(articleService.getAll());
            // Bind the list to the TableView
            userTableView.setItems(articles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addArticlerB(ActionEvent actionEvent) {
    }
}
