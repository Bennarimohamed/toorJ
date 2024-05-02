package com.example.toor1.controller;

import com.example.toor1.entity.User;
import com.example.toor1.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddUserController {

    @FXML
    private TextField addressTF;

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
    private DatePicker birthdateTF;

    @FXML
    private TextField roleTF;

    private UserService userService;

    @FXML
    public void initialize() {
        try {
            userService = new UserService();
        } catch (RuntimeException e) {
            System.err.println("Error initializing UserService: " + e.getMessage());
        }
    }

    @FXML
    void addButton(ActionEvent event) {
        // Vérification de la saisie des champs obligatoires
        if (nameTF.getText().isEmpty() ||
                lastnameTF.getText().isEmpty() ||
                emailTF.getText().isEmpty() ||
                passwordTF.getText().isEmpty() ||
                addressTF.getText().isEmpty() ||
                countryTF.getText().isEmpty() ||
                birthdateTF.getValue() == null ||
                roleTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Tous les champs sont obligatoires.");
            return;
        }

        // Vérification du format de l'email
        if (!emailTF.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert(Alert.AlertType.ERROR, "Adresse email invalide.");
            return;
        }

        // Vérification de la longueur du mot de passe
        if (passwordTF.getText().length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }

        // Vérification que la date de naissance est une date dans le passé
        LocalDate today = LocalDate.now();
        LocalDate selectedDate = birthdateTF.getValue();
        if (selectedDate.isAfter(today)) {
            showAlert(Alert.AlertType.ERROR, "La date de naissance doit être dans le passé.");
            return;
        }

        try {
            User user = new User();
            user.setName(nameTF.getText());
            user.setLastname(lastnameTF.getText());
            user.setEmail(emailTF.getText());
            user.setAddress(addressTF.getText());
            user.setCountry(countryTF.getText());
            user.setPassword(passwordTF.getText());
            user.setPhoto(photoTF.getText());
            user.setBirthdate(java.sql.Date.valueOf(selectedDate));
            user.setRoles(roleTF.getText());

            // Ajout du nouvel utilisateur
            userService.add(user);

            // Redirection vers HomeUser après l'ajout
            Parent root = FXMLLoader.load(getClass().getResource("/HomeUser.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur lors de la redirection vers HomeUser : " + e.getMessage());
        }
    }

    @FXML
    void backtolist(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomeUser.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Erreur lors du retour à HomeUser : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    void LogoutB(ActionEvent event) { // Ajoutez le paramètre ActionEvent
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error loading Login.fxml: " + e.getMessage()); // Correction de l'erreur de chargement
        }
    }

    public void backToHomeB(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/HomeUser.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error loading Login.fxml: " + e.getMessage()); // Correction de l'erreur de chargement
        }
    }
}
