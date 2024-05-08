package tn.espirt.pi.GUI.Hotel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.espirt.pi.DB.HotelCRUD;
import tn.espirt.pi.Entities.Hotel;
import tn.espirt.pi.GUI.Reservation.AddReservationController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowHotelController implements Initializable {

    public Label nameLabel1;
    @FXML
    private Label nameLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label facilitiesLabel;
    @FXML
    private Label availabilityLabel;

    private HotelCRUD hotelCRUD;
    private Hotel hotel;
    @FXML
    private ImageView hotelImg;
    @FXML
    private Button backBTN;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hotelCRUD = new HotelCRUD(); // Assuming you have a default constructor or similar setup
    }

    void initializeWithHotelId(int id) {
        hotel = hotelCRUD.getHotel(id);
        if (hotel != null) {
            nameLabel1.setText(hotel.getName());
            nameLabel.setText(hotel.getName());
            categoryLabel.setText(hotel.getCategorie());
            addressLabel.setText(hotel.getAddress());
            descriptionLabel.setText(hotel.getDescription());
            phoneLabel.setText(hotel.getInfoline());
            facilitiesLabel.setText(hotel.getFacilities());
            availabilityLabel.setText(hotel.isAvaibility() ? "Available" : "Not Available");
            hotelImg.setImage(new Image(hotel.getPhoto(), true));
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        closeStage();
    }

    @FXML
    private void makeReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReservation.fxml"));
            Parent root = loader.load();
            AddReservationController controller = loader.getController();
            controller.initializeWithHotelId(hotel.getId());
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
            Logger.getLogger(EditHotelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
