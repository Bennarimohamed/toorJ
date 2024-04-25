package com.example.demo.entity;

import java.util.Date;

public class Article {
    private int id;
    private String image;
    private String title;
    private String categorie;
    private String content;
    private Date createdat;
    private String metacontent;

    // Constructors
    public Article(int id, String image, String title, String categorie, String content, Date createdat, String metacontent) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.categorie = categorie;
        this.content = content;
        this.createdat = createdat;
        this.metacontent = metacontent;
    }

    public Article(String image, String title, String categorie, String content, Date createdat, String metacontent) {
        this.image = image;
        this.title = title;
        this.categorie = categorie;
        this.content = content;
        this.createdat = createdat;
        this.metacontent = metacontent;
    }

    public Article() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public String getMetacontent() {
        return metacontent;
    }

    public void setMetacontent(String metacontent) {
        this.metacontent = metacontent;
    }

    // toString method
    @Override
    public String toString() {
        return "YourEntityName{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", categorie='" + categorie + '\'' +
                ", content='" + content + '\'' +
                ", createdat=" + createdat +
                ", metacontent='" + metacontent + '\'' +
                '}';
    }
}
