package com.henallux.testmenu.Model;

public class Nurse {

    private String nom;
    private String prenom;
    private String numeroTel;
    private String email;
    private Boolean typeChef;

    public Nurse(String nom, String prenom, String numeroTel, String email, Boolean typeChef) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTel = numeroTel;
        this.email = email;
        this.typeChef = typeChef;
    }

    public Nurse() { }

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

    public Boolean getTypeChef() {
        return typeChef;
    }

    public void setTypeChef(Boolean typeChef) {
        this.typeChef = typeChef;
    }
}
