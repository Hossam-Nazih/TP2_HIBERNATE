package com.example.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

// Une salle physique pouvant etre reservee par un utilisateur.
@Entity
public class Salle {

    // IDENTITY delegue la generation de la cle a la base (auto-increment H2),
    // plus simple ici qu'une sequence puisqu'on reste sur une seule base H2.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifiant;

    // unique=true car deux salles ne peuvent pas porter le meme intitule dans le batiment
    @Column(nullable = false, unique = true)
    private String intitule;

    @Column(nullable = false)
    private int capaciteMax;

    public Salle() {}

    public Salle(String intitule, int capaciteMax) {
        this.intitule = intitule;
        this.capaciteMax = capaciteMax;
    }

    public Long getIdentifiant() {
        return identifiant;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    @Override
    public String toString() {
        return "Salle [identifiant=" + identifiant + ", intitule=" + intitule + ", capaciteMax=" + capaciteMax + "]";
    }
}
