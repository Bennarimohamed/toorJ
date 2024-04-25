package com.example.toor1.controller;

import com.example.toor1.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.toor1.entity.User;

public class Login {

    @FXML
    private TextField emailLOGIN;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordLOGIN;

    @FXML
    private Button registerButton; // Button for the registration action

    @FXML
    void loginB(ActionEvent event) {
        // Get the email and password entered by the user
        String email = emailLOGIN.getText();
        String password = passwordLOGIN.getText();

        // Initialize the UserService
        UserService userService = new UserService();

        try {
            // Check if the user exists and the password matches
            User user = userService.getByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                // Redirect to HomeUser if the login is successful
                Parent root = FXMLLoader.load(getClass().getResource("/HomeUser.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                // Show alert if login fails
                showAlert("Login Failed", "Invalid email or password. Please try again.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred during login: " + e.getMessage());
        }
    }

    @FXML
    void registerB(ActionEvent event) {
        try {
            // Redirect to Register.fxml when the register button is clicked
            Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "An error occurred during registration: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
