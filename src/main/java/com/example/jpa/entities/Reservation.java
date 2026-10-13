package com.example.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

// Occupation d'une salle par un utilisateur sur un creneau donne.
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifiant;

    @Column(nullable = false)
    private LocalDateTime debut;

    @Column(nullable = false)
    private LocalDateTime fin;

    // EnumType.STRING stocke le libelle (ex. "CONFIRMEE") plutot que l'index numerique,
    // ce qui reste lisible si on consulte la table directement
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation etat;

    @Column
    private String objet;

    @Column(nullable = false)
    private int effectifPrevu;

    @ManyToOne
    @JoinColumn(name = "salle_reservee_id", nullable = false)
    private Salle salleReservee;

    @ManyToOne
    @JoinColumn(name = "demandeur_id", nullable = false)
    private Utilisateur demandeur;

    public Reservation() {}

    public Reservation(LocalDateTime debut, LocalDateTime fin, String objet,
                        int effectifPrevu, Salle salleReservee, Utilisateur demandeur) {
        this.debut = debut;
        this.fin = fin;
        this.etat = StatutReservation.EN_ATTENTE;
        this.objet = objet;
        this.effectifPrevu = effectifPrevu;
        this.salleReservee = salleReservee;
        this.demandeur = demandeur;
    }

    public Long getIdentifiant() {
        return identifiant;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public StatutReservation getEtat() {
        return etat;
    }

    public void setEtat(StatutReservation etat) {
        this.etat = etat;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public int getEffectifPrevu() {
        return effectifPrevu;
    }

    public void setEffectifPrevu(int effectifPrevu) {
        this.effectifPrevu = effectifPrevu;
    }

    public Salle getSalleReservee() {
        return salleReservee;
    }

    public void setSalleReservee(Salle salleReservee) {
        this.salleReservee = salleReservee;
    }

    public Utilisateur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
    }

    @Override
    public String toString() {
        return "Reservation [identifiant=" + identifiant
                + ", salleReservee=" + (salleReservee != null ? salleReservee.getIntitule() : "aucune")
                + ", demandeur=" + (demandeur != null ? demandeur.getNomComplet() : "aucun")
                + ", debut=" + debut + ", fin=" + fin
                + ", etat=" + etat + ", effectifPrevu=" + effectifPrevu + "]";
    }
}
