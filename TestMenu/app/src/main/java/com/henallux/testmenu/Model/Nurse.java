package com.henallux.testmenu.Model;

import java.io.Serializable;

public class Nurse implements Serializable{

    private Integer id;
    private String nom;
    private String prenom;
    private String numeroTel;
    private String email;
    private String typeChef;
    private String login;
    private String password;

    public Nurse(Integer id, String nom, String prenom, String numeroTel, String email, String typeChef, String login, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTel = numeroTel;
        this.email = email;
        this.typeChef = typeChef;
        this.login = login;
        this.password = password;
    }

    public Nurse() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeChef() {
        return typeChef;
    }

    public void setTypeChef(String typeChef) {
        this.typeChef = typeChef;
    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
