package tn.espirt.pi.GUI.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.espirt.pi.DB.BookingCRUD;
import tn.espirt.pi.DB.DB;
import tn.espirt.pi.DB.HotelCRUD;
import tn.espirt.pi.Entities.Booking;
import tn.espirt.pi.Entities.User;
import tn.espirt.pi.GUI.Hotel.EditHotelController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddReservationController implements Initializable {

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
        if(!"Admin".equals(User.role)){
            clientIdField.setOpacity(0);
            clientIdField.setText(Integer.toString(User.id));
        }
    }

    @FXML
    private void handleAddReservation(ActionEvent event) {
        try {
            int hotelId = Integer.parseInt(hotelIdField.getText());
            if (!hotelExists(hotelId)) {
                showAlert("Error", "No hotel found with ID: " + hotelId, AlertType.ERROR);
                return;
            }

            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();
            if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut)) {
                showAlert("Error", "Check-in date must be before check-out date.", AlertType.ERROR);
                return;
            }

            int nbAdults = Integer.parseInt(nbAdultsField.getText());
            int nbChildren = Integer.parseInt(nbChildrenField.getText());
            if (nbAdults < 0 || nbChildren < 0 || (nbAdults == 0 && nbChildren == 0)) {
                showAlert("Error", "You must specify at least one adult or child, and numbers must be positive.", AlertType.ERROR);
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

            // Sender's email and password
            String senderEmail = "mustaphaturki999@gmail.com";
            String senderPassword = "gixdwxwqlmjeisxu";

            // Recipient's email
            String recipientEmail = "mustaphaturki999@gmail.com";

            // SMTP server configuration
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Create a session with authentication
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });
            System.out.println(session);

            try {
                // Create a MimeMessage object
                Message message = new MimeMessage(session);

                // Set From: header field of the header
                message.setFrom(new InternetAddress(senderEmail));

                // Set To: header field of the header
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

                // Set Subject: header field
                message.setSubject("Reservation");

                // Now set the actual message
                // Now set the actual message
                message.setText("Thank you for making a reservation with us! Your booking has been successfully confirmed.");


                // Send message
                Transport.send(message);

                System.out.println("Email sent successfully");

            } catch (MessagingException e) {
                System.out.println(e.toString());
            }

            new BookingCRUD().addBooking(booking);
            showAlert("Success", "Reservation successfully added!", AlertType.INFORMATION);
            closeStage();

        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid input: " + ex.getMessage(), AlertType.ERROR);
        } catch (Exception ex) {
            showAlert("Error", "An error occurred: " + ex.getMessage(), AlertType.ERROR);
        }
    }

    private boolean hotelExists(int hotelId) {
        return hotelCRUD.getHotel(hotelId) != null;
    }

    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

    //gixd wxwq lmje isxu
    @FXML
    private void goBack(ActionEvent event) {
        closeStage();
    }

    public void initializeWithHotelId(int id) {
        hotelIdField.setText(Integer.toString(id));
    }
}
