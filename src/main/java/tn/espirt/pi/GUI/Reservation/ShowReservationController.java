/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.espirt.pi.GUI.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.espirt.pi.DB.BookingCRUD;
import tn.espirt.pi.Entities.Booking;
import tn.espirt.pi.GUI.Hotel.EditHotelController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Aziza
 */
public class ShowReservationController implements Initializable {

    @FXML
    private TextField idField;
    @FXML
    private TextField clientIdField;
    @FXML
    private TextField hotelIdField;
    @FXML
    private TextField checkInField;
    @FXML
    private TextField checkOutField;
    @FXML
    private TextField nbAdultsField;
    @FXML
    private TextField nbChildrenField;
    @FXML
    private TextField spRequestField;
    @FXML
    private Button backBTN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goBack(ActionEvent event) {
        closeStage();
    }
    
        
    private void closeStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardReservation.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Reservations");
            stage.show();
            ((Stage) backBTN.getScene().getWindow()).close();
        } catch (IOException ex) {
            Logger.getLogger(EditHotelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

void initializeWithReservationId(int id) {
        Booking booking = new BookingCRUD().getBookingById(id);  // Fetch booking details
        if (booking != null) {
            populateFields(booking);
        } else {
            Logger.getLogger(ShowReservationController.class.getName()).log(Level.SEVERE, "No booking found with ID: " + id);
            Alert alert = new Alert(Alert.AlertType.ERROR, "No reservation found for ID: " + id, ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void populateFields(Booking booking) {
        idField.setText(String.valueOf(booking.getId()));
        clientIdField.setText(String.valueOf(booking.getClientId()));
        hotelIdField.setText(String.valueOf(booking.getHotelId()));
        checkInField.setText(booking.getCheckin().toString());
        checkOutField.setText(booking.getCheckout().toString());
        nbAdultsField.setText(String.valueOf(booking.getNbAdults()));
        nbChildrenField.setText(String.valueOf(booking.getNbChildrens()));
        spRequestField.setText(booking.getSpRequest());
        //disable all fields
        idField.setDisable(true);
        clientIdField.setDisable(true);
        hotelIdField.setDisable(true);
        checkInField.setDisable(true);
        checkOutField.setDisable(true);
        nbAdultsField.setDisable(true);
        nbChildrenField.setDisable(true);
        spRequestField.setDisable(true);
        

    }
}
