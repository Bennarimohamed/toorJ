/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.espirt.pi.GUI.Hotel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.espirt.pi.DB.HotelCRUD;
import tn.espirt.pi.Entities.Hotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditHotelController implements Initializable {

    @FXML
    private TextField imageURL;
    @FXML
    private TextField hotelName;
    @FXML
    private TextField hotelCategorie;
    @FXML
    private TextField hotelAdd;
    @FXML
    private TextField hotelDesc;
    @FXML
    private TextField ppnHotel;
    @FXML
    private TextField infoLineHotel;
    @FXML
    private TextField facilitiesHotel;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button backBTN;
    @FXML
    private CheckBox availibiltyHotel;

    private HotelCRUD hotelCRUD;
    private Hotel hotel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hotelCRUD = new HotelCRUD();
    }

    @FXML
    private void confirmHotel(ActionEvent event) {
        if (validateInput()) {
            Hotel hotelE = new Hotel(
                    this.hotel.getId(),
                    imageURL.getText(),
                    hotelName.getText(),
                    hotelCategorie.getText(),
                    hotelAdd.getText(),
                    hotelDesc.getText(),
                    Double.parseDouble(ppnHotel.getText()),
                    infoLineHotel.getText(),
                    facilitiesHotel.getText(),
                    availibiltyHotel.isSelected()
            );
            new HotelCRUD().editHotel(hotelE);
            closeStage();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please veridy your inputs.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        closeStage();

    }

    private boolean validateInput() {
        boolean validInfoLine = isNumeric(infoLineHotel.getText()) && infoLineHotel.getText().length() == 8;

        boolean validFacilities = isPositiveInteger(facilitiesHotel.getText());

        boolean validPpn = isPositiveDouble(ppnHotel.getText());

        boolean validURL = imageURL.getText().startsWith("http://") || imageURL.getText().startsWith("https://");

        boolean validOtherFields = !hotelName.getText().isEmpty()
                && !hotelCategorie.getText().isEmpty()
                && !hotelAdd.getText().isEmpty();

        return validInfoLine && validFacilities && validPpn && validURL && validOtherFields;
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPositiveInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            int value = Integer.parseInt(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPositiveDouble(String str) {
        if (str == null) {
            return false;
        }
        try {
            double value = Double.parseDouble(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void closeStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowHotels.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Show Hotels");
            stage.show();
            ((Stage) backBTN.getScene().getWindow()).close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void initializeWithHotelId(int id) {
        hotel = hotelCRUD.getHotel(id);
        if (hotel != null) {
            imageURL.setText(hotel.getPhoto());
            hotelName.setText(hotel.getName());
            hotelCategorie.setText(hotel.getCategorie());
            hotelAdd.setText(hotel.getAddress());
            hotelDesc.setText(hotel.getDescription());
            ppnHotel.setText(String.valueOf(hotel.getPppn()));
            infoLineHotel.setText(hotel.getInfoline());
            facilitiesHotel.setText(String.valueOf(hotel.getFacilities()));
            availibiltyHotel.setSelected(hotel.isAvaibility());
        }

    }

}
