/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.espirt.pi.Entities;

import java.sql.Date;

public class Booking {
    private int id;
    private int clientId;
    private int hotelId;
    private Date checkin;
    private Date checkout;
    private int nbAdults;
    private int nbChildrens;
    private String spRequest;

    public Booking(int id, int clientId, int hotelId, Date checkin, Date checkout, int nbAdults, int nbChildrens, String spRequest) {
        this.id = id;
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.nbAdults = nbAdults;
        this.nbChildrens = nbChildrens;
        this.spRequest = spRequest;
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public int getNbAdults() {
        return nbAdults;
    }

    public void setNbAdults(int nbAdults) {
        this.nbAdults = nbAdults;
    }

    public int getNbChildrens() {
        return nbChildrens;
    }

    public void setNbChildrens(int nbChildrens) {
        this.nbChildrens = nbChildrens;
    }

    public String getSpRequest() {
        return spRequest;
    }

    public void setSpRequest(String spRequest) {
        this.spRequest = spRequest;
    }

}

