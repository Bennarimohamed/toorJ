/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.espirt.pi.GUI.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.espirt.pi.DB.BookingCRUD;
import tn.espirt.pi.Entities.Booking;
import tn.espirt.pi.Entities.User;
import tn.espirt.pi.GUI.Hotel.ShowHotelsController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardReservationController implements Initializable {

    @FXML
    private Button showHotelBTN;
    @FXML
    private Button addResBtn;
    @FXML
    private Button editResrvBtn;
    @FXML
    private Button deleteReservBtn;
    @FXML
    private TableView<Booking> reservTableView;
    @FXML
    private TableColumn<Booking, Integer> idTB;
    @FXML
    private TableColumn<Booking, Integer> clientIDTB;
    @FXML
    private TableColumn<Booking, Integer> hotelID;
    @FXML
    private TableColumn<Booking, Date> checkINTB;
    @FXML
    private TableColumn<Booking, Date> checkOutTB;
    @FXML
    private TableColumn<Booking, Integer> nbAdultTB;
    @FXML
    private TableColumn<Booking, Integer> nbChildTB;
    @FXML
    private TableColumn<Booking, String> sRequestTB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTB.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientIDTB.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        hotelID.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        checkINTB.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        checkOutTB.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        nbAdultTB.setCellValueFactory(new PropertyValueFactory<>("nbAdults"));
        nbChildTB.setCellValueFactory(new PropertyValueFactory<>("nbChildrens"));
        sRequestTB.setCellValueFactory(new PropertyValueFactory<>("spRequest"));
        loadReservations();
    }

    private void loadReservations() {
        if ("Admin".equals(User.role)) {
            BookingCRUD bookingCRUD = new BookingCRUD();
            ObservableList<Booking> bookingsList = FXCollections.observableArrayList(bookingCRUD.getBookings());
            reservTableView.setItems(bookingsList);
        } else {
            BookingCRUD bookingCRUD = new BookingCRUD();
            ObservableList<Booking> bookingsList = FXCollections.observableArrayList(bookingCRUD.getBookingByClient(User.id));
            reservTableView.setItems(bookingsList);
        }

    }

    @FXML
    private void getHotelsView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowHotels.fxml"));
            Parent root = loader.load();
            ShowHotelsController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Hotels");
            stage.show();

            closeStage();
        } catch (IOException ex) {
            Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getReservationView(ActionEvent event) {
    }

    @FXML
    private void showResrv(ActionEvent event) {
        Booking selectedBooking = reservTableView.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select a reservation to view details.", ButtonType.OK);
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReservation.fxml"));
                Parent root = loader.load();
                ShowReservationController controller = loader.getController();
                controller.initializeWithReservationId(selectedBooking.getId());

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Edit Reservation");
                stage.show();
                closeStage();
            } catch (IOException ex) {
                Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addReserv(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReservation.fxml"));
            Parent root = loader.load();
            AddReservationController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Hotel");
            stage.show();
            closeStage();
        } catch (IOException ex) {
            Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editReserv(ActionEvent event) {
        Booking selectedBooking = reservTableView.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select a reservation to edit details.", ButtonType.OK);
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditReservation.fxml"));
                Parent root = loader.load();
                EditReservationController controller = loader.getController();
                controller.initializeWithReservationId(selectedBooking.getId());

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Edit Reservation");
                stage.show();
                closeStage();
            } catch (IOException ex) {
                Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteReserv(ActionEvent event) {
            Booking selectedBooking = reservTableView.getSelectionModel().getSelectedItem();
    if (selectedBooking == null) {
        // Alert the user to select a reservation if none is selected
        Alert alert = new Alert(AlertType.WARNING, "Please select a reservation to delete.", ButtonType.OK);
        alert.showAndWait();
        return;
    }

    // Confirm the deletion with the user
    Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete the selected reservation? This action cannot be undone.", ButtonType.YES, ButtonType.NO);
    confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            // Perform the deletion
            try {
                new BookingCRUD().removeBooking(selectedBooking);
                // Refresh the table data
                loadReservations();

                // Inform the user of successful deletion
                Alert infoAlert = new Alert(AlertType.INFORMATION, "Reservation successfully deleted.", ButtonType.OK);
                infoAlert.showAndWait();
            } catch (Exception e) {
                // Log the exception and show an error message
                Logger.getLogger(DashboardReservationController.class.getName()).log(Level.SEVERE, null, e);
                Alert errorAlert = new Alert(AlertType.ERROR, "Error deleting reservation: " + e.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }
        }
    });
    }

    private void closeStage() {
        ((Stage) reservTableView.getScene().getWindow()).close();
    }
}
