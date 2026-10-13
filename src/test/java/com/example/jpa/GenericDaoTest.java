package com.example.jpa;

import com.example.jpa.dao.GenericDao;
import com.example.jpa.entities.Salle;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GenericDaoTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static GenericDao<Salle> salleDao;

    @BeforeClass
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("gestionPU");
        em = emf.createEntityManager();
        salleDao = new GenericDao<>(Salle.class, em);
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void crudLifecycle() {
        Salle salle = new Salle("Salle DAO Test", 20);
        salleDao.create(salle);
        Long identifiant = salle.getIdentifiant();
        assertTrue(identifiant != null);

        Salle found = salleDao.find(identifiant);
        assertEquals("Salle DAO Test", found.getIntitule());

        found.setCapaciteMax(30);
        salleDao.update(found);
        assertEquals(30, salleDao.find(identifiant).getCapaciteMax());

        List<Salle> toutes = salleDao.findAll();
        assertTrue(toutes.stream().anyMatch(s -> s.getIdentifiant().equals(identifiant)));

        salleDao.delete(found);
        assertNull(salleDao.find(identifiant));
    }
}
