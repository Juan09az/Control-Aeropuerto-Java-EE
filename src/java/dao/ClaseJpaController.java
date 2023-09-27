/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import entities.Clase;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Reserva;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class ClaseJpaController implements Serializable {

    public ClaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clase clase) throws PreexistingEntityException, Exception {
        if (clase.getReservaList() == null) {
            clase.setReservaList(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : clase.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getReservaPK());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            clase.setReservaList(attachedReservaList);
            em.persist(clase);
            for (Reserva reservaListReserva : clase.getReservaList()) {
                Clase oldCodClaseOfReservaListReserva = reservaListReserva.getCodClase();
                reservaListReserva.setCodClase(clase);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldCodClaseOfReservaListReserva != null) {
                    oldCodClaseOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldCodClaseOfReservaListReserva = em.merge(oldCodClaseOfReservaListReserva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClase(clase.getCodClase()) != null) {
                throw new PreexistingEntityException("Clase " + clase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clase clase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase persistentClase = em.find(Clase.class, clase.getCodClase());
            List<Reserva> reservaListOld = persistentClase.getReservaList();
            List<Reserva> reservaListNew = clase.getReservaList();
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getReservaPK());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            clase.setReservaList(reservaListNew);
            clase = em.merge(clase);
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    reservaListOldReserva.setCodClase(null);
                    reservaListOldReserva = em.merge(reservaListOldReserva);
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Clase oldCodClaseOfReservaListNewReserva = reservaListNewReserva.getCodClase();
                    reservaListNewReserva.setCodClase(clase);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldCodClaseOfReservaListNewReserva != null && !oldCodClaseOfReservaListNewReserva.equals(clase)) {
                        oldCodClaseOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldCodClaseOfReservaListNewReserva = em.merge(oldCodClaseOfReservaListNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = clase.getCodClase();
                if (findClase(id) == null) {
                    throw new NonexistentEntityException("The clase with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase clase;
            try {
                clase = em.getReference(Clase.class, id);
                clase.getCodClase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clase with id " + id + " no longer exists.", enfe);
            }
            List<Reserva> reservaList = clase.getReservaList();
            for (Reserva reservaListReserva : reservaList) {
                reservaListReserva.setCodClase(null);
                reservaListReserva = em.merge(reservaListReserva);
            }
            em.remove(clase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clase> findClaseEntities() {
        return findClaseEntities(true, -1, -1);
    }

    public List<Clase> findClaseEntities(int maxResults, int firstResult) {
        return findClaseEntities(false, maxResults, firstResult);
    }

    private List<Clase> findClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clase.class));
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

    public Clase findClase(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clase.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clase> rt = cq.from(Clase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
