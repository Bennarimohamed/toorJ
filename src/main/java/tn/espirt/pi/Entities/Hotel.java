/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.espirt.pi.Entities;

public class Hotel {
    private int id;
    private String photo;
    private String name;
    private String categorie;
    private String address;
    private String description;
    private double pppn;
    private String infoline;
    private String facilities;
    private boolean avaibility;

    public Hotel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPppn() {
        return pppn;
    }

    public void setPppn(double pppn) {
        this.pppn = pppn;
    }

    public String getInfoline() {
        return infoline;
    }

    public void setInfoline(String infoline) {
        this.infoline = infoline;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public boolean isAvaibility() {
        return avaibility;
    }

    public void setAvaibility(boolean avaibility) {
        this.avaibility = avaibility;
    }

    public Hotel(int id, String photo, String name, String categorie, String address, String description, double pppn, String infoline, String facilities, boolean avaibility) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.categorie = categorie;
        this.address = address;
        this.description = description;
        this.pppn = pppn;
        this.infoline = infoline;
        this.facilities = facilities;
        this.avaibility = avaibility;
    }

}