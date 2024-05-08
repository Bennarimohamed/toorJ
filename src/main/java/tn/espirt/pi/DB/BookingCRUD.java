/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.espirt.pi.DB;



import tn.espirt.pi.Entities.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingCRUD {
    private final Connection con = DB.getInstance().getCnx();
    
    public void addBooking(Booking b) {
        String sqlInsert = "INSERT INTO reservations (client_id, hotel_id, checkin, checkout, nbadults, nbchildrens, sprequest) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = con.prepareStatement(sqlInsert)) {
            pstmt.setInt(1, b.getClientId());
            pstmt.setInt(2, b.getHotelId());
            pstmt.setDate(3, b.getCheckin());
            pstmt.setDate(4, b.getCheckout());
            pstmt.setInt(5, b.getNbAdults());
            pstmt.setInt(6, b.getNbChildrens());
            pstmt.setString(7, b.getSpRequest());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM reservations;";
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setClientId(rs.getInt("client_id"));
                b.setHotelId(rs.getInt("hotel_id"));
                b.setCheckin(rs.getDate("checkin"));
                b.setCheckout(rs.getDate("checkout"));
                b.setNbAdults(rs.getInt("nbadults"));
                b.setNbChildrens(rs.getInt("nbchildrens"));
                b.setSpRequest(rs.getString("sprequest"));
                bookings.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    public void removeBooking(Booking b) {
        String sqlDelete = "DELETE FROM reservations WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, b.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void editBooking(Booking b) {
        String sqlUpdate = "UPDATE reservations SET client_id = ?, hotel_id = ?, checkin = ?, checkout = ?, nbadults = ?, nbchildrens = ?, sprequest = ? WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, b.getClientId());
            pstmt.setInt(2, b.getHotelId());
            pstmt.setDate(3, b.getCheckin());
            pstmt.setDate(4, b.getCheckout());
            pstmt.setInt(5, b.getNbAdults());
            pstmt.setInt(6, b.getNbChildrens());
            pstmt.setString(7, b.getSpRequest());
            pstmt.setInt(8, b.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Booking getBookingById(int id) {
        Booking b = new Booking();
        String sql = "SELECT * FROM reservations WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    b.setId(rs.getInt("id"));
                    b.setClientId(rs.getInt("client_id"));
                    b.setHotelId(rs.getInt("hotel_id"));
                    b.setCheckin(rs.getDate("checkin"));
                    b.setCheckout(rs.getDate("checkout"));
                    b.setNbAdults(rs.getInt("nbadults"));
                    b.setNbChildrens(rs.getInt("nbchildrens"));
                    b.setSpRequest(rs.getString("sprequest"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Booking getBookingByClient(int clientId) {
        Booking b = new Booking();
        String sql = "SELECT * FROM reservations WHERE client_id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    b.setId(rs.getInt("id"));
                    b.setClientId(rs.getInt("client_id"));
                    b.setHotelId(rs.getInt("hotel_id"));
                    b.setCheckin(rs.getDate("checkin"));
                    b.setCheckout(rs.getDate("checkout"));
                    b.setNbAdults(rs.getInt("nbadults"));
                    b.setNbChildrens(rs.getInt("nbchildrens"));
                    b.setSpRequest(rs.getString("sprequest"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Booking getBookingByHotel(int hotelId) {
        Booking b = new Booking();
        String sql = "SELECT * FROM reservations WHERE hotel_id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, hotelId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    b.setId(rs.getInt("id"));
                    b.setClientId(rs.getInt("client_id"));
                    b.setHotelId(rs.getInt("hotel_id"));
                    b.setCheckin(rs.getDate("checkin"));
                    b.setCheckout(rs.getDate("checkout"));
                    b.setNbAdults(rs.getInt("nbadults"));
                    b.setNbChildrens(rs.getInt("nbchildrens"));
                    b.setSpRequest(rs.getString("sprequest"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }
}
