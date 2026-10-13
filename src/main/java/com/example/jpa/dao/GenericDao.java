package com.example.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

// Regroupe le CRUD commun a toutes les entites pour eviter de repeter
// la meme gestion de transaction dans chaque DAO specifique.
public class GenericDao<T> {

    private final Class<T> entityClass;
    protected EntityManager em;

    public GenericDao(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
    }

    public void create(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (RuntimeException e) {
            // on annule uniquement si la transaction est encore active : commit() peut
            // avoir deja echoue apres avoir fini son travail dans certains cas
            if (tx.isActive()) tx.rollback();
            System.err.println("Echec de creation : " + e.getMessage());
            throw e;
        }
    }

    public T find(Object id) {
        // lecture simple, pas besoin de transaction
        return em.find(entityClass, id);
    }

    public T update(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // merge() et non persist() : l'entite peut provenir d'un contexte deja ferme
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Echec de mise a jour : " + e.getMessage());
            throw e;
        }
    }

    public void delete(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // si l'entite n'est pas managee par le contexte courant, il faut d'abord la rattacher
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Echec de suppression : " + e.getMessage());
            throw e;
        }
    }

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }
}
