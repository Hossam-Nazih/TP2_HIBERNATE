package com.example.jpa.service;

import com.example.jpa.dao.ReservationDao;
import com.example.jpa.dao.SalleDao;
import com.example.jpa.dao.UtilisateurDao;
import com.example.jpa.entities.Reservation;
import com.example.jpa.entities.Salle;
import com.example.jpa.entities.StatutReservation;
import com.example.jpa.entities.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

// Facade metier : regroupe les DAO et expose des operations pretes a l'emploi
// pour ne pas exposer EntityManager/transactions au reste de l'application.
public class ServiceGestion {

    private EntityManagerFactory emf;
    private EntityManager em;
    private SalleDao salleDao;
    private UtilisateurDao utilisateurDao;
    private ReservationDao reservationDao;

    public ServiceGestion() {
        emf = Persistence.createEntityManagerFactory("gestionPU");
        em = emf.createEntityManager();
        salleDao = new SalleDao(em);
        utilisateurDao = new UtilisateurDao(em);
        reservationDao = new ReservationDao(em);
    }

    public void fermer() {
        em.close();
        emf.close();
    }

    public void ajouterSalle(Salle salle) {
        salleDao.create(salle);
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurDao.create(utilisateur);
    }

    public List<Salle> listerSalles() {
        return salleDao.findAll();
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurDao.findAll();
    }

    public List<Utilisateur> utilisateursParSalle(String nomSalle) {
        return utilisateurDao.findBySalleNom(nomSalle);
    }

    // verifie d'abord qu'aucune reservation existante ne chevauche le creneau demande
    // avant de confirmer, pour ne jamais double-booker une salle
    public Reservation reserver(LocalDateTime debut, LocalDateTime fin, String objet,
                                 int effectifPrevu, Salle salleReservee, Utilisateur demandeur) {
        List<Reservation> conflits = reservationDao.findChevauchements(salleReservee.getIntitule(), debut, fin);
        if (!conflits.isEmpty()) {
            throw new IllegalStateException("La salle " + salleReservee.getIntitule() + " est deja reservee sur ce creneau");
        }

        Reservation reservation = new Reservation(debut, fin, objet, effectifPrevu, salleReservee, demandeur);
        reservation.setEtat(StatutReservation.CONFIRMEE);
        reservationDao.create(reservation);
        return reservation;
    }

    public List<Reservation> listerReservations() {
        return reservationDao.findAll();
    }

    public List<Reservation> reservationsParSalle(String nomSalle) {
        return reservationDao.findBySalleNom(nomSalle);
    }

    public void annulerReservation(Reservation reservation) {
        reservation.setEtat(StatutReservation.ANNULEE);
        reservationDao.update(reservation);
    }
}
