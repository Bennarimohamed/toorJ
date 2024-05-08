/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package tn.espirt.pi.projetpi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Aziza
 */
public class ProjetPI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/ShowHotels.fxml"));  // Update the path to your FXML file

            // Create the Scene
            Scene scene = new Scene(root);

            // Set the scene to the stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotel Management System");  // Set the title of the window
            primaryStage.show();  // Display the stage
        } catch (Exception e) {
            System.out.println("test");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }

}
