package com.example.toor1.controller;

import com.example.toor1.entity.User;
import com.example.toor1.services.UserService;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Date;

public class EditUserController {

    @FXML
    private Button SaveUserButton;

    @FXML
    private TextField addressNewValue;

    @FXML
    private Button backtolistButton;

    @FXML
    private TextField birthdateNewValue;

    @FXML
    private TextField countryNewValue;

    @FXML
    private TextField emailNewValue;

    @FXML
    private TextField idNewValue;

    @FXML
    private TextField lastnameNewValue;

    @FXML
    private TextField nameNewValue;

    @FXML
    private TextField passwordNewValue;

    @FXML
    private TextField photoNewValue;

    @FXML
    private Label photoValue;

    @FXML
    private TextField roleNewValue;

    @FXML
    void SaveUserB(ActionEvent event) throws Exception {
        try {
            UserService userService = new UserService();

            // Retrieve values from the input fields
            int id = Integer.parseInt(idNewValue.getText()); // Convert to integer
            String name = nameNewValue.getText();
            String lastname = lastnameNewValue.getText();
            String email = emailNewValue.getText();
            String password = passwordNewValue.getText();
            String address = addressNewValue.getText();
            String country = countryNewValue.getText();
            String birthdateStr = birthdateNewValue.getText(); // Date as string
            String roles = roleNewValue.getText();
            String photo = photoNewValue.getText();

            // Validate required fields
            if (name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || birthdateStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "All required fields must be filled in.");
                return; // Stop if any required field is missing
            }

            // Check email format
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                showAlert(Alert.AlertType.WARNING, "Invalid email format.");
                return; // Stop if email format is invalid
            }

            // Check password length
            if (password.length() < 8) {
                showAlert(Alert.AlertType.WARNING, "Password must be at least 8 characters long.");
                return; // Stop if password is too short
            }

            // Check if birthdate is a valid date and is in the past
            Date birthdate;
            try {
                birthdate = Date.valueOf(birthdateStr); // Convert string to Date
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.WARNING, "Invalid birthdate format.");
                return; // Stop if birthdate is invalid
            }

            if (birthdate.after(new Date(System.currentTimeMillis()))) { // Ensure birthdate is in the past
                showAlert(Alert.AlertType.WARNING, "Birthdate must be in the past.");
                return; // Stop if birthdate is in the future
            }

            // Create or update the user
            User user = new User(id, email, roles, password, photo, name, lastname, birthdate, address, country);

            // Determine if updating or adding a new user
            if (userService.getById(id) != null) {
                // If a user with this ID exists, update it
                userService.update(user);
            } else {
                // Otherwise, add a new user
                userService.add(user);
            }

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "User data saved successfully!");

            // Delay to allow the alert to be visible before redirecting
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/HomeUser.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ex) {
                    System.err.println("Error loading HomeUser.fxml: " + ex.getMessage());
                }
            });
            delay.play();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error saving user data: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
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
            System.err.println("Error loading HomeUser.fxml: " + e.getMessage());
        }
    }

    public void setUserData(User user) {
        idNewValue.setText(String.valueOf(user.getId())); // Assuming ID is an integer
        nameNewValue.setText(user.getName());
        lastnameNewValue.setText(user.getLastname());
        emailNewValue.setText(user.getEmail());
        passwordNewValue.setText(user.getPassword());
        addressNewValue.setText(user.getAddress());
        countryNewValue.setText(user.getCountry());
        birthdateNewValue.setText(user.getBirthdate().toString()); // Assuming birthdate is of type `Date`
        roleNewValue.setText(user.getRoles());
        photoNewValue.setText(user.getPhoto());
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

    @FXML
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
