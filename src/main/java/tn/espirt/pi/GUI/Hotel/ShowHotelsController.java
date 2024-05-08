/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.espirt.pi.GUI.Hotel;


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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.espirt.pi.DB.HotelCRUD;
import tn.espirt.pi.Entities.Hotel;
import tn.espirt.pi.Entities.User;
import tn.espirt.pi.GUI.Reservation.DashboardReservationController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowHotelsController implements Initializable {

    public TextField searchTextField;
    @FXML
    private Button showHotelBTN;
    @FXML
    private Button addHotelBtn;
    @FXML
    private Button editHotelBtn;
    @FXML
    private Button deleteHotelBtn;
    @FXML
    private TableView<Hotel> hotelTableView;
    @FXML
    private TableColumn<Hotel, Integer> idTB;
    @FXML
    private TableColumn<Hotel, String> nameTB;
    @FXML
    private TableColumn<Hotel, String> categTB;
    @FXML
    private TableColumn<Hotel, String> addrTB;
    @FXML
    private TableColumn<Hotel, String> descTB;
    @FXML
    private TableColumn<Hotel, String> phoneTB;
    @FXML
    private TableColumn<Hotel, String> facilitiesTB;
    @FXML
    private TableColumn<Hotel, Boolean> AvailiTB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTB.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTB.setCellValueFactory(new PropertyValueFactory<>("name"));
        categTB.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        addrTB.setCellValueFactory(new PropertyValueFactory<>("address"));
        descTB.setCellValueFactory(new PropertyValueFactory<>("description"));
        phoneTB.setCellValueFactory(new PropertyValueFactory<>("infoline"));
        facilitiesTB.setCellValueFactory(new PropertyValueFactory<>("facilities"));
        AvailiTB.setCellValueFactory(new PropertyValueFactory<>("avaibility"));
        if (!"Admin".equals(User.role)) {
            addHotelBtn.setOpacity(0);
            editHotelBtn.setOpacity(0);
            deleteHotelBtn.setOpacity(0);
        }
        loadHotelData();
    }

    @FXML
    private void showHotel(ActionEvent event) {
        Hotel selectedHotel = hotelTableView.getSelectionModel().getSelectedItem();
        if (selectedHotel == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select a hotel to view details.", ButtonType.OK);
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowHotel.fxml"));
                Parent root = loader.load();
                ShowHotelController controller = loader.getController();
                controller.initializeWithHotelId(selectedHotel.getId());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                closeStage();
            } catch (IOException ex) {
                Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addHotel(ActionEvent event) {
 
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HotelAdd.fxml"));
                Parent root = loader.load();
                HotelAddController controller = loader.getController();

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
    private void editHotel(ActionEvent event) {
        Hotel selectedHotel = hotelTableView.getSelectionModel().getSelectedItem();
        if (selectedHotel == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select a hotel to edit details.", ButtonType.OK);
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditHotel.fxml"));
                Parent root = loader.load();
                EditHotelController controller = loader.getController();
                controller.initializeWithHotelId(selectedHotel.getId());

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Edit Hotel");
                stage.show();
                closeStage();
            } catch (IOException ex) {
                Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void closeStage() {
        ((Stage) hotelTableView.getScene().getWindow()).close();
    }

@FXML
private void deleteHotel(ActionEvent event) {
    Hotel selectedHotel = hotelTableView.getSelectionModel().getSelectedItem();
    if (selectedHotel == null) {
        // Alert the user to select a hotel if none is selected
        Alert alert = new Alert(AlertType.WARNING, "Please select a hotel to delete.", ButtonType.OK);
        alert.showAndWait();
        return;
    }

    // Confirm the deletion with the user
    Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete the selected hotel? This action cannot be undone.", ButtonType.YES, ButtonType.NO);
    confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            // Perform the deletion
            try {
                new HotelCRUD().removeHotel(selectedHotel);
                // Refresh the table data
                loadHotelData();

                // Inform the user of successful deletion
                Alert infoAlert = new Alert(AlertType.INFORMATION, "Hotel successfully deleted.", ButtonType.OK);
                infoAlert.showAndWait();
            } catch (Exception e) {
                // Log the exception and show an error message
                Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, e);
                Alert errorAlert = new Alert(AlertType.ERROR, "Error deleting hotel: " + e.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }
        }
    });
}

    private void loadHotelData() {
        HotelCRUD hotelCRUD = new HotelCRUD();
        ObservableList<Hotel> hotelList = FXCollections.observableArrayList(hotelCRUD.getHotels());
        hotelTableView.setItems(hotelList);
    }

    @FXML
    private void showResvDash(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardReservation.fxml"));
            Parent root = loader.load();
            DashboardReservationController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Reservations");
            stage.show();

            closeStage();
        } catch (IOException ex) {
            Logger.getLogger(ShowHotelsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchHotel(KeyEvent keyEvent) {
        String searchText = searchTextField.getText().toLowerCase(); // Assuming searchTextField is your TextField for input text
        if(searchText.equals("")){
            this.loadHotelData();
            return;
        }
        HotelCRUD hotelCRUD = new HotelCRUD();

        ObservableList<Hotel> filteredList = FXCollections.observableArrayList();
        ObservableList<Hotel> hotelList = FXCollections.observableArrayList(hotelCRUD.getHotels());
        // Iterate over the data in the table
        for (Hotel hotel : hotelList) {
            // Check if any of the parameters contain the search text
            if (String.valueOf(hotel.getId()).contains(searchText) ||
                    hotel.getName().toLowerCase().contains(searchText) ||
                    hotel.getCategorie().toLowerCase().contains(searchText) ||
                    hotel.getAddress().toLowerCase().contains(searchText) ||
                    hotel.getDescription().toLowerCase().contains(searchText) ||
                    hotel.getInfoline().toLowerCase().contains(searchText) ||
                    hotel.getFacilities().toLowerCase().contains(searchText))
                    {
                filteredList.add(hotel); // Add the matching hotel to the filtered list
            }
        }

        // Clear the existing data in the table
        hotelTableView.getItems().clear();

        // Add the filtered rows back into the table
        hotelTableView.setItems(filteredList);


    }
}
