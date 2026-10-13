package com.example.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

// Une personne qui peut se voir attribuer une salle et y faire des reservations.
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifiant;

    @Column(nullable = false)
    private String nomComplet;

    // unique=true : on identifie un utilisateur par son email dans les recherches metier
    @Column(nullable = false, unique = true)
    private String adresseEmail;

    // ManyToOne : plusieurs utilisateurs peuvent partager la meme salle, mais chacun n'en a qu'une
    @ManyToOne
    @JoinColumn(name = "salle_attribuee_id")
    private Salle salleAttribuee;

    public Utilisateur() {}

    public Utilisateur(String nomComplet, String adresseEmail, Salle salleAttribuee) {
        this.nomComplet = nomComplet;
        this.adresseEmail = adresseEmail;
        this.salleAttribuee = salleAttribuee;
    }

    public Long getIdentifiant() {
        return identifiant;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public Salle getSalleAttribuee() {
        return salleAttribuee;
    }

    public void setSalleAttribuee(Salle salleAttribuee) {
        this.salleAttribuee = salleAttribuee;
    }

    @Override
    public String toString() {
        return "Utilisateur [identifiant=" + identifiant + ", nomComplet=" + nomComplet
                + ", adresseEmail=" + adresseEmail
                + ", salleAttribuee=" + (salleAttribuee != null ? salleAttribuee.getIntitule() : "aucune") + "]";
    }
}
