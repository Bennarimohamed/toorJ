package com.example.toor1.controller;

import com.example.toor1.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShowUserController {

    @FXML
    private Label addressValue;

    @FXML
    private Button backtolistButton;

    @FXML
    private Label birthdateValue;

    @FXML
    private Label countryValue;

    @FXML
    private Label emailValue;

    @FXML
    private Label idValue;

    @FXML
    private Label lastnameValue;

    @FXML
    private Label nameValue;

    @FXML
    private Label passwordValue;

    @FXML
    private Label photoValue;

    @FXML
    private Label roleValue;

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

    public void setUserDetails(User user) {
        idValue.setText(String.valueOf(user.getId()));
        nameValue.setText(user.getName());
        lastnameValue.setText(user.getLastname());
        emailValue.setText(user.getEmail());
        passwordValue.setText(user.getPassword());
        addressValue.setText(user.getAddress());
        countryValue.setText(user.getCountry());
        birthdateValue.setText(user.getBirthdate().toString());
        roleValue.setText(user.getRoles());
        photoValue.setText(user.getPhoto()); // Depending on how you're handling photos
    }

}
