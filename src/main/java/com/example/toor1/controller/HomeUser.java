package com.example.toor1.controller;

import com.example.toor1.entity.User;
import com.example.toor1.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomeUser {

    @FXML
    private TableColumn<?, ?> addressTB;

    @FXML
    private TableColumn<?, ?> emailTB;

    @FXML
    private TableColumn<?, ?> idTB;

    @FXML
    private TableColumn<?, ?> lastnameTB;

    @FXML
    private TableColumn<?, ?> nameTB;

    @FXML
    private TableColumn<?, ?> roleTB;

    @FXML
    private TableColumn<?, ?> photoTB;  // Added column for photo

    @FXML
    private TableColumn<?, ?> passwordTB;  // Added column for password

    @FXML
    private TableColumn<?, ?> countryTB;  // Added column for country

    @FXML
    private TableColumn<?, ?> birthdateTB;  // New column for birthdate

    @FXML
    private TableView<User> userTableView;

    @FXML
    public void initialize() {  // Initialization method
        // Configure the TableColumns with PropertyValueFactory
        idTB.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTB.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameTB.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        roleTB.setCellValueFactory(new PropertyValueFactory<>("roles"));  // Corrected "roles"
        emailTB.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressTB.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Added additional columns to the initialization
        photoTB.setCellValueFactory(new PropertyValueFactory<>("photo"));
        passwordTB.setCellValueFactory(new PropertyValueFactory<>("password"));
        countryTB.setCellValueFactory(new PropertyValueFactory<>("country"));
        birthdateTB.setCellValueFactory(new PropertyValueFactory<>("birthdate"));  // Added birthdate

        // Initialize the TableView with a list of Users from a UserService
        initializeUserTableView();
    }

    private void initializeUserTableView() {
        UserService userService = new UserService();
        try {
            // Fetch the list of all users from the database
            ObservableList<User> users = FXCollections.observableArrayList(userService.getAll());
            // Bind the list to the TableView
            userTableView.setItems(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addUserB(javafx.event.ActionEvent event) {  // Use javafx.event.ActionEvent
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error loading User.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void showUserB(javafx.event.ActionEvent event) {
        try {
            User selectedUser = userTableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowUser.fxml"));
                Parent root = loader.load();

                // Get the controller instance of the loaded FXML
                ShowUserController controller = loader.getController();
                // Pass the user details to the controller
                controller.setUserDetails(selectedUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("No user selected");
            }
        } catch (Exception e) {
            System.err.println("Error loading ShowUser.fxml: " + e.getMessage());
        }
    }

    @FXML
    void editUserB(ActionEvent event) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                // Load the EditUser FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditUser.fxml"));
                Parent root = loader.load();

                // Get the controller of the loaded FXML
                EditUserController editUserController = loader.getController();

                // Pass the selected user's data to the controller
                editUserController.setUserData(selectedUser);

                // Redirect to the EditUser page
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                System.err.println("Error loading EditUser.fxml: " + e.getMessage());
            }
        } else {
            System.out.println("No user selected.");
        }
    }

    @FXML
    void deleteUserB(ActionEvent event) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Create a confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete User");
            alert.setContentText("Are you sure you want to delete this user?");

            // Add buttons for confirmation and cancellation
            ButtonType confirmDelete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelDelete = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmDelete, cancelDelete);

            alert.showAndWait().ifPresent(response -> {
                if (response == confirmDelete) {
                    try {
                        UserService userService = new UserService();
                        userService.delete(selectedUser.getId()); // Delete the user from the database

                        // Refresh the table view
                        ObservableList<User> users = FXCollections.observableArrayList(userService.getAll());
                        userTableView.setItems(users);

                        // Show a success message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.");
                        successAlert.show();

                    } catch (Exception e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error deleting user: " + e.getMessage());
                        errorAlert.show();
                    }
                }
            });

        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING, "No user selected.");
            noSelectionAlert.show();
        }
    }

}
