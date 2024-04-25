package com.example.toor1.controller;

import com.example.toor1.entity.User;
import com.example.toor1.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Register {

    @FXML
    private TextField addressTF;

    @FXML
    private Button backtologinButton;

    @FXML
    private DatePicker birthdateTF;

    @FXML
    private TextField countryTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField lastnameTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private TextField photoTF;

    @FXML
    private Button registerButton;

    @FXML
    private TextField roleTF;

    @FXML
    void backtologinB(ActionEvent event) {
        try {
            // Redirect to Login.fxml when the back-to-login button is clicked
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while trying to return to the login page: " + e.getMessage());
        }
    }

    @FXML
    void registerB(ActionEvent event) {
        try {
            // Collect user information from the form
            String name = nameTF.getText();
            String lastname = lastnameTF.getText();
            String email = emailTF.getText();
            String password = passwordTF.getText();
            String address = addressTF.getText();
            String country = countryTF.getText();
            String photo = photoTF.getText();
            LocalDate birthdate = birthdateTF.getValue();
            String role = roleTF.getText();

            // Check for empty fields
            if (name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || country.isEmpty() || role.isEmpty()) {
                showAlert("Registration Failed", "Please fill in all required fields.");
                return;
            }

            // Vérification du format de l'email
            if (!emailTF.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                showAlert(String.valueOf(Alert.AlertType.ERROR), "Adresse email invalide.");
                return;
            }

            // Vérification de la longueur du mot de passe
            if (passwordTF.getText().length() < 8) {
                showAlert(String.valueOf(Alert.AlertType.ERROR), "Le mot de passe doit contenir au moins 8 caractères.");
                return;
            }

            // Vérification que la date de naissance est une date dans le passé
            LocalDate today = LocalDate.now();
            LocalDate selectedDate = birthdateTF.getValue();
            if (selectedDate.isAfter(today)) {
                showAlert(String.valueOf(Alert.AlertType.ERROR), "La date de naissance doit être dans le passé.");
                return;
            }

            // Create a new User object
            User newUser = new User();
            newUser.setName(name);
            newUser.setLastname(lastname);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setAddress(address);
            newUser.setCountry(country);
            newUser.setPhoto(photo);
            newUser.setRoles(role);

            if (birthdate != null) {
                newUser.setBirthdate(java.sql.Date.valueOf(birthdate));
            }

            // Add the new user to the database
            UserService userService = new UserService();
            userService.add(newUser);

            // If registration is successful, redirect to the login page
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            showAlert("Registration Successful", "Your account has been created. Please log in.");

        } catch (SQLException e) {
            showAlert("Error", "An error occurred while adding the user to the database: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
