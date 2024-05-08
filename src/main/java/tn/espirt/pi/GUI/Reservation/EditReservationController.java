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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.espirt.pi.DB.BookingCRUD;
import tn.espirt.pi.DB.HotelCRUD;
import tn.espirt.pi.Entities.Booking;
import tn.espirt.pi.Entities.User;
import tn.espirt.pi.GUI.Hotel.EditHotelController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Aziza
 */
public class EditReservationController implements Initializable {

    @FXML
    private TextField clientIdField;
    @FXML
    private TextField hotelIdField;
    @FXML
    private DatePicker checkInPicker;
    @FXML
    private DatePicker checkOutPicker;
    @FXML
    private TextField nbAdultsField;
    @FXML
    private TextField nbChildrenField;
    @FXML
    private TextField specialRequestField;

    private HotelCRUD hotelCRUD = new HotelCRUD();
    @FXML
    private Button backBTN;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!"Admin".equals(User.role)) {
            clientIdField.setOpacity(0);
            clientIdField.setText(Integer.toString(User.id));
        }
    }

    @FXML
    private void handleAddReservation(ActionEvent event) {
        try {
            int hotelId = Integer.parseInt(hotelIdField.getText());
            if (!hotelExists(hotelId)) {
                showAlert("Error", "No hotel found with ID: " + hotelId, Alert.AlertType.ERROR);
                return;
            }

            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();
            if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut)) {
                showAlert("Error", "Check-in date must be before check-out date.", Alert.AlertType.ERROR);
                return;
            }

            int nbAdults = Integer.parseInt(nbAdultsField.getText());
            int nbChildren = Integer.parseInt(nbChildrenField.getText());
            if (nbAdults < 0 || nbChildren < 0 || (nbAdults == 0 && nbChildren == 0)) {
                showAlert("Error", "You must specify at least one adult or child, and numbers must be positive.", Alert.AlertType.ERROR);
                return;
            }

            Booking booking = new Booking();
            booking.setClientId(Integer.parseInt(clientIdField.getText()));
            booking.setHotelId(hotelId);
            booking.setCheckin(Date.valueOf(checkIn));
            booking.setCheckout(Date.valueOf(checkOut));
            booking.setNbAdults(nbAdults);
            booking.setNbChildrens(nbChildren);
            booking.setSpRequest(specialRequestField.getText());

            new BookingCRUD().addBooking(booking);
            showAlert("Success", "Reservation successfully edited!", Alert.AlertType.INFORMATION);
            closeStage();

        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid input: " + ex.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception ex) {
            showAlert("Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean hotelExists(int hotelId) {
        return hotelCRUD.getHotel(hotelId) != null;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    void initializeWithReservationId(int id) {
        Booking booking = new BookingCRUD().getBookingById(id);
        if (booking != null) {
            populateFields(booking);
        } else {
            showAlert("Error", "No reservation found with ID: " + id, Alert.AlertType.ERROR);
        }
    }

    private void populateFields(Booking booking) {
        clientIdField.setText(Integer.toString(booking.getClientId()));
        hotelIdField.setText(Integer.toString(booking.getHotelId()));
        checkInPicker.setValue(booking.getCheckin().toLocalDate());
        checkOutPicker.setValue(booking.getCheckout().toLocalDate());
        nbAdultsField.setText(Integer.toString(booking.getNbAdults()));
        nbChildrenField.setText(Integer.toString(booking.getNbChildrens()));
        specialRequestField.setText(booking.getSpRequest());

        if (!"Admin".equals(User.role)) {
            clientIdField.setDisable(true);
        } else {
            clientIdField.setDisable(false);
        }
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

}
