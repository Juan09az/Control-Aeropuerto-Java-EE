/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Aeropuerto;
import entities.Origen;
import entities.OrigenPK;
import entities.Vuelo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class OrigenJpaController implements Serializable {

    public OrigenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Origen origen) throws PreexistingEntityException, Exception {
        if (origen.getOrigenPK() == null) {
            origen.setOrigenPK(new OrigenPK());
        }
        origen.getOrigenPK().setIata(origen.getAeropuerto().getIata());
        origen.getOrigenPK().setCodVuelo(origen.getVuelo().getCodVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aeropuerto aeropuerto = origen.getAeropuerto();
            if (aeropuerto != null) {
                aeropuerto = em.getReference(aeropuerto.getClass(), aeropuerto.getIata());
                origen.setAeropuerto(aeropuerto);
            }
            Vuelo vuelo = origen.getVuelo();
            if (vuelo != null) {
                vuelo = em.getReference(vuelo.getClass(), vuelo.getCodVuelo());
                origen.setVuelo(vuelo);
            }
            em.persist(origen);
            if (aeropuerto != null) {
                aeropuerto.getOrigenList().add(origen);
                aeropuerto = em.merge(aeropuerto);
            }
            if (vuelo != null) {
                vuelo.getOrigenList().add(origen);
                vuelo = em.merge(vuelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrigen(origen.getOrigenPK()) != null) {
                throw new PreexistingEntityException("Origen " + origen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Origen origen) throws NonexistentEntityException, Exception {
        origen.getOrigenPK().setIata(origen.getAeropuerto().getIata());
        origen.getOrigenPK().setCodVuelo(origen.getVuelo().getCodVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Origen persistentOrigen = em.find(Origen.class, origen.getOrigenPK());
            Aeropuerto aeropuertoOld = persistentOrigen.getAeropuerto();
            Aeropuerto aeropuertoNew = origen.getAeropuerto();
            Vuelo vueloOld = persistentOrigen.getVuelo();
            Vuelo vueloNew = origen.getVuelo();
            if (aeropuertoNew != null) {
                aeropuertoNew = em.getReference(aeropuertoNew.getClass(), aeropuertoNew.getIata());
                origen.setAeropuerto(aeropuertoNew);
            }
            if (vueloNew != null) {
                vueloNew = em.getReference(vueloNew.getClass(), vueloNew.getCodVuelo());
                origen.setVuelo(vueloNew);
            }
            origen = em.merge(origen);
            if (aeropuertoOld != null && !aeropuertoOld.equals(aeropuertoNew)) {
                aeropuertoOld.getOrigenList().remove(origen);
                aeropuertoOld = em.merge(aeropuertoOld);
            }
            if (aeropuertoNew != null && !aeropuertoNew.equals(aeropuertoOld)) {
                aeropuertoNew.getOrigenList().add(origen);
                aeropuertoNew = em.merge(aeropuertoNew);
            }
            if (vueloOld != null && !vueloOld.equals(vueloNew)) {
                vueloOld.getOrigenList().remove(origen);
                vueloOld = em.merge(vueloOld);
            }
            if (vueloNew != null && !vueloNew.equals(vueloOld)) {
                vueloNew.getOrigenList().add(origen);
                vueloNew = em.merge(vueloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                OrigenPK id = origen.getOrigenPK();
                if (findOrigen(id) == null) {
                    throw new NonexistentEntityException("The origen with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OrigenPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Origen origen;
            try {
                origen = em.getReference(Origen.class, id);
                origen.getOrigenPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The origen with id " + id + " no longer exists.", enfe);
            }
            Aeropuerto aeropuerto = origen.getAeropuerto();
            if (aeropuerto != null) {
                aeropuerto.getOrigenList().remove(origen);
                aeropuerto = em.merge(aeropuerto);
            }
            Vuelo vuelo = origen.getVuelo();
            if (vuelo != null) {
                vuelo.getOrigenList().remove(origen);
                vuelo = em.merge(vuelo);
            }
            em.remove(origen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Origen> findOrigenEntities() {
        return findOrigenEntities(true, -1, -1);
    }

    public List<Origen> findOrigenEntities(int maxResults, int firstResult) {
        return findOrigenEntities(false, maxResults, firstResult);
    }

    private List<Origen> findOrigenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Origen.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Origen findOrigen(OrigenPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Origen.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrigenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Origen> rt = cq.from(Origen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
