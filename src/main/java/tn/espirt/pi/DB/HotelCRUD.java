package tn.espirt.pi.DB;


import tn.espirt.pi.Entities.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelCRUD {

    private final Connection con = DB.getInstance().getCnx();

    public void addHotel(Hotel h) {
        String sqlInsert = "INSERT INTO hotels (photo, name, categorie, address, description, pppn, infoline, facilities, avaibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = con.prepareStatement(sqlInsert)) {
            pstmt.setString(1, h.getPhoto());
            pstmt.setString(2, h.getName());
            pstmt.setString(3, h.getCategorie());
            pstmt.setString(4, h.getAddress());
            pstmt.setString(5, h.getDescription());
            pstmt.setDouble(6, h.getPppn());
            pstmt.setString(7, h.getInfoline());
            pstmt.setString(8, h.getFacilities());
            pstmt.setBoolean(9, h.isAvaibility());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels;";
        try (PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Hotel h = new Hotel();
                h.setId(rs.getInt("id"));
                h.setPhoto(rs.getString("photo"));
                h.setName(rs.getString("name"));
                h.setCategorie(rs.getString("categorie"));
                h.setAddress(rs.getString("address"));
                h.setDescription(rs.getString("description"));
                h.setPppn(rs.getDouble("pppn"));
                h.setInfoline(rs.getString("infoline"));
                h.setFacilities(rs.getString("facilities"));
                h.setAvaibility(rs.getBoolean("avaibility"));
                hotels.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public void removeHotel(Hotel h) {
        String sqlDelete = "DELETE FROM hotels WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, h.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editHotel(Hotel h) {
        String sqlUpdate = "UPDATE hotels SET photo = ?, name = ?, categorie = ?, address = ?, description = ?, pppn = ?, infoline = ?, facilities = ?, avaibility = ? WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, h.getPhoto());
            pstmt.setString(2, h.getName());
            pstmt.setString(3, h.getCategorie());
            pstmt.setString(4, h.getAddress());
            pstmt.setString(5, h.getDescription());
            pstmt.setDouble(6, h.getPppn());
            pstmt.setString(7, h.getInfoline());
            pstmt.setString(8, h.getFacilities());
            pstmt.setBoolean(9, h.isAvaibility());
            pstmt.setInt(10, h.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Hotel getHotel(int id){
        Hotel h = new Hotel();
        String sql = "SELECT * FROM hotels WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    h.setId(rs.getInt("id"));
                    h.setPhoto(rs.getString("photo"));
                    h.setName(rs.getString("name"));
                    h.setCategorie(rs.getString("categorie"));
                    h.setAddress(rs.getString("address"));
                    h.setDescription(rs.getString("description"));
                    h.setPppn(rs.getDouble("pppn"));
                    h.setInfoline(rs.getString("infoline"));
                    h.setFacilities(rs.getString("facilities"));
                    h.setAvaibility(rs.getBoolean("avaibility"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return h;
        
    }
}
