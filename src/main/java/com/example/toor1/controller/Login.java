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
        String email = emailLOGIN.getText();
        String password = passwordLOGIN.getText();

        UserService userService = new UserService();

        try {
            User user = userService.getByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                if (user.isBlocked()) { // Utilisez le booléen pour vérifier le blocage
                    showAlert("Accès refusé", "Votre compte est bloqué.");
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("/HomeUser.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                showAlert("Connexion échouée", "E-mail ou mot de passe invalide.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'exception pour aider au diagnostic
            showAlert("Erreur", "Une erreur s'est produite lors de la connexion.");
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
