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
import entities.Destino;
import entities.DestinoPK;
import entities.Vuelo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class DestinoJpaController implements Serializable {

    public DestinoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Destino destino) throws PreexistingEntityException, Exception {
        if (destino.getDestinoPK() == null) {
            destino.setDestinoPK(new DestinoPK());
        }
        destino.getDestinoPK().setIata(destino.getAeropuerto().getIata());
        destino.getDestinoPK().setCodVuelo(destino.getVuelo().getCodVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aeropuerto aeropuerto = destino.getAeropuerto();
            if (aeropuerto != null) {
                aeropuerto = em.getReference(aeropuerto.getClass(), aeropuerto.getIata());
                destino.setAeropuerto(aeropuerto);
            }
            Vuelo vuelo = destino.getVuelo();
            if (vuelo != null) {
                vuelo = em.getReference(vuelo.getClass(), vuelo.getCodVuelo());
                destino.setVuelo(vuelo);
            }
            em.persist(destino);
            if (aeropuerto != null) {
                aeropuerto.getDestinoList().add(destino);
                aeropuerto = em.merge(aeropuerto);
            }
            if (vuelo != null) {
                vuelo.getDestinoList().add(destino);
                vuelo = em.merge(vuelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDestino(destino.getDestinoPK()) != null) {
                throw new PreexistingEntityException("Destino " + destino + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Destino destino) throws NonexistentEntityException, Exception {
        destino.getDestinoPK().setIata(destino.getAeropuerto().getIata());
        destino.getDestinoPK().setCodVuelo(destino.getVuelo().getCodVuelo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Destino persistentDestino = em.find(Destino.class, destino.getDestinoPK());
            Aeropuerto aeropuertoOld = persistentDestino.getAeropuerto();
            Aeropuerto aeropuertoNew = destino.getAeropuerto();
            Vuelo vueloOld = persistentDestino.getVuelo();
            Vuelo vueloNew = destino.getVuelo();
            if (aeropuertoNew != null) {
                aeropuertoNew = em.getReference(aeropuertoNew.getClass(), aeropuertoNew.getIata());
                destino.setAeropuerto(aeropuertoNew);
            }
            if (vueloNew != null) {
                vueloNew = em.getReference(vueloNew.getClass(), vueloNew.getCodVuelo());
                destino.setVuelo(vueloNew);
            }
            destino = em.merge(destino);
            if (aeropuertoOld != null && !aeropuertoOld.equals(aeropuertoNew)) {
                aeropuertoOld.getDestinoList().remove(destino);
                aeropuertoOld = em.merge(aeropuertoOld);
            }
            if (aeropuertoNew != null && !aeropuertoNew.equals(aeropuertoOld)) {
                aeropuertoNew.getDestinoList().add(destino);
                aeropuertoNew = em.merge(aeropuertoNew);
            }
            if (vueloOld != null && !vueloOld.equals(vueloNew)) {
                vueloOld.getDestinoList().remove(destino);
                vueloOld = em.merge(vueloOld);
            }
            if (vueloNew != null && !vueloNew.equals(vueloOld)) {
                vueloNew.getDestinoList().add(destino);
                vueloNew = em.merge(vueloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DestinoPK id = destino.getDestinoPK();
                if (findDestino(id) == null) {
                    throw new NonexistentEntityException("The destino with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DestinoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Destino destino;
            try {
                destino = em.getReference(Destino.class, id);
                destino.getDestinoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The destino with id " + id + " no longer exists.", enfe);
            }
            Aeropuerto aeropuerto = destino.getAeropuerto();
            if (aeropuerto != null) {
                aeropuerto.getDestinoList().remove(destino);
                aeropuerto = em.merge(aeropuerto);
            }
            Vuelo vuelo = destino.getVuelo();
            if (vuelo != null) {
                vuelo.getDestinoList().remove(destino);
                vuelo = em.merge(vuelo);
            }
            em.remove(destino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Destino> findDestinoEntities() {
        return findDestinoEntities(true, -1, -1);
    }

    public List<Destino> findDestinoEntities(int maxResults, int firstResult) {
        return findDestinoEntities(false, maxResults, firstResult);
    }

    private List<Destino> findDestinoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Destino.class));
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

    public Destino findDestino(DestinoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Destino.class, id);
        } finally {
            em.close();
        }
    }

    public int getDestinoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Destino> rt = cq.from(Destino.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
