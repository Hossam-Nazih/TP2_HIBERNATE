package com.example.jpa.dao;

import com.example.jpa.entities.Salle;
import javax.persistence.EntityManager;
import java.util.List;

public class SalleDao extends GenericDao<Salle> {

    public SalleDao(EntityManager em) {
        super(Salle.class, em);
    }

    // utile par ex. pour proposer uniquement des salles compatibles avec un effectif donne
    public List<Salle> findByCapaciteMin(int capaciteMin) {
        return em.createQuery("SELECT s FROM Salle s WHERE s.capaciteMax >= :capaciteMin", Salle.class)
                 .setParameter("capaciteMin", capaciteMin)
                 .getResultList();
    }
}
