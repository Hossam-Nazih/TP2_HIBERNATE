package com.example.jpa.dao;

import com.example.jpa.entities.Reservation;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDao extends GenericDao<Reservation> {

    public ReservationDao(EntityManager em) {
        super(Reservation.class, em);
    }

    public List<Reservation> findBySalleNom(String nomSalle) {
        return em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.salleReservee.intitule = :nomSalle", Reservation.class)
                .setParameter("nomSalle", nomSalle)
                .getResultList();
    }

    // deux creneaux se chevauchent si l'un commence avant que l'autre finisse, dans les deux sens :
    // debut existant < fin demandee ET fin existante > debut demande
    public List<Reservation> findChevauchements(String nomSalle, LocalDateTime debut, LocalDateTime fin) {
        return em.createQuery(
                        "SELECT r FROM Reservation r " +
                                "WHERE r.salleReservee.intitule = :nomSalle " +
                                "AND r.debut < :fin AND r.fin > :debut",
                        Reservation.class)
                .setParameter("nomSalle", nomSalle)
                .setParameter("debut", debut)
                .setParameter("fin", fin)
                .getResultList();
    }
}
