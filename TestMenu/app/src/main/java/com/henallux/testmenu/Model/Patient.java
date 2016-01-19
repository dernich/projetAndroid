package com.henallux.testmenu.Model;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable {

    private Integer id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String numeroTel;
    private String email;
    private String rue;
    private String numeroMaison;
    private String codePostal;
    private String localite;
    private String dateDebutSoin;
    private String dateFinSoin;

    public Patient() {

    }

    public Patient(Integer id, String nom, String prenom, String dateNaissance, String numeroTel, String rue, String numeroMaison, String codePostal, String localite, String dateDebutSoin, String dateFinSoin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numeroTel = numeroTel;
        this.rue = rue;
        this.numeroMaison = numeroMaison;
        this.codePostal = codePostal;
        this.localite = localite;
        this.dateDebutSoin = dateDebutSoin;
        this.dateFinSoin = dateFinSoin;
    }

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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
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

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNumeroMaison() {
        return numeroMaison;
    }

    public void setNumeroMaison(String numeroMaison) {
        this.numeroMaison = numeroMaison;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getDateDebutSoin() {
        return dateDebutSoin;
    }

    public void setDateDebutSoin(String dateDebutSoin) {
        this.dateDebutSoin = dateDebutSoin;
    }

    public String getDateFinSoin() {
        return dateFinSoin;
    }

    public void setDateFinSoin(String dateFinSoin) {
        this.dateFinSoin = dateFinSoin;
    }

    public String toString() {
        return nom;
    }
}
