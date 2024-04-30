package com.example.toor1.entity;

import java.util.Date;

public class User {
    private int id;  // Auto-incrementing field, so no need to initialize with a default value
    private String email;
    private String roles;  // This could represent roles as a comma-separated string or a JSON string
    private String password;
    private String photo;
    private String name;
    private String lastname;
    private Date birthdate;  // Use LocalDate for date representation
    private String address;
    private String country;
    private boolean isBlocked;


    // Constructors
    public User(int id, String email, String roles, String password, String photo, String name,
                String lastname, Date birthdate, String address, String country) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.photo = photo;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.country = country;
    }

    public User(String email, String roles, String password, String photo, String name,
                String lastname, Date birthdate, String address, String country) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.photo = photo;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.country = country;
    }

    public User(int id, String email, String roles, String password, String photo, String name, String lastname,
                Date birthdate, String address, String country, boolean isBlocked) {
        this(id, email, roles, password, photo, name, lastname, birthdate, address, country); // Appel au constructeur précédent
        this.isBlocked = isBlocked; // Ajouter le champ supplémentaire
    }

    public User() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Getters and setters for isBlocked
    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

