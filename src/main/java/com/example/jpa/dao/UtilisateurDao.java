package com.example.jpa.dao;

import com.example.jpa.entities.Utilisateur;
import javax.persistence.EntityManager;
import java.util.List;

public class UtilisateurDao extends GenericDao<Utilisateur> {

    public UtilisateurDao(EntityManager em) {
        super(Utilisateur.class, em);
    }

    // jointure implicite JPQL via la relation salleAttribuee -> pas besoin d'ecrire un JOIN explicite
    public List<Utilisateur> findBySalleNom(String nomSalle) {
        return em.createQuery("SELECT u FROM Utilisateur u WHERE u.salleAttribuee.intitule = :nomSalle", Utilisateur.class)
                 .setParameter("nomSalle", nomSalle)
                 .getResultList();
    }
}
