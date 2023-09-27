/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import entities.Pasajero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Persona;
import entities.Usuario;
import entities.Reserva;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class PasajeroJpaController implements Serializable {

    public PasajeroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pasajero pasajero) throws PreexistingEntityException, Exception {
        if (pasajero.getReservaList() == null) {
            pasajero.setReservaList(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona idPersona = pasajero.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                pasajero.setIdPersona(idPersona);
            }
            Usuario usuarioPas = pasajero.getUsuarioPas();
            if (usuarioPas != null) {
                usuarioPas = em.getReference(usuarioPas.getClass(), usuarioPas.getUsuario());
                pasajero.setUsuarioPas(usuarioPas);
            }
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : pasajero.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getReservaPK());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            pasajero.setReservaList(attachedReservaList);
            em.persist(pasajero);
            if (idPersona != null) {
                idPersona.getPasajeroList().add(pasajero);
                idPersona = em.merge(idPersona);
            }
            if (usuarioPas != null) {
                usuarioPas.getPasajeroList().add(pasajero);
                usuarioPas = em.merge(usuarioPas);
            }
            for (Reserva reservaListReserva : pasajero.getReservaList()) {
                Pasajero oldPasajeroOfReservaListReserva = reservaListReserva.getPasajero();
                reservaListReserva.setPasajero(pasajero);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldPasajeroOfReservaListReserva != null) {
                    oldPasajeroOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldPasajeroOfReservaListReserva = em.merge(oldPasajeroOfReservaListReserva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPasajero(pasajero.getIdPasajero()) != null) {
                throw new PreexistingEntityException("Pasajero " + pasajero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pasajero pasajero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero persistentPasajero = em.find(Pasajero.class, pasajero.getIdPasajero());
            Persona idPersonaOld = persistentPasajero.getIdPersona();
            Persona idPersonaNew = pasajero.getIdPersona();
            Usuario usuarioPasOld = persistentPasajero.getUsuarioPas();
            Usuario usuarioPasNew = pasajero.getUsuarioPas();
            List<Reserva> reservaListOld = persistentPasajero.getReservaList();
            List<Reserva> reservaListNew = pasajero.getReservaList();
            List<String> illegalOrphanMessages = null;
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its pasajero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                pasajero.setIdPersona(idPersonaNew);
            }
            if (usuarioPasNew != null) {
                usuarioPasNew = em.getReference(usuarioPasNew.getClass(), usuarioPasNew.getUsuario());
                pasajero.setUsuarioPas(usuarioPasNew);
            }
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getReservaPK());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            pasajero.setReservaList(reservaListNew);
            pasajero = em.merge(pasajero);
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getPasajeroList().remove(pasajero);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getPasajeroList().add(pasajero);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (usuarioPasOld != null && !usuarioPasOld.equals(usuarioPasNew)) {
                usuarioPasOld.getPasajeroList().remove(pasajero);
                usuarioPasOld = em.merge(usuarioPasOld);
            }
            if (usuarioPasNew != null && !usuarioPasNew.equals(usuarioPasOld)) {
                usuarioPasNew.getPasajeroList().add(pasajero);
                usuarioPasNew = em.merge(usuarioPasNew);
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Pasajero oldPasajeroOfReservaListNewReserva = reservaListNewReserva.getPasajero();
                    reservaListNewReserva.setPasajero(pasajero);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldPasajeroOfReservaListNewReserva != null && !oldPasajeroOfReservaListNewReserva.equals(pasajero)) {
                        oldPasajeroOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldPasajeroOfReservaListNewReserva = em.merge(oldPasajeroOfReservaListNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pasajero.getIdPasajero();
                if (findPasajero(id) == null) {
                    throw new NonexistentEntityException("The pasajero with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero pasajero;
            try {
                pasajero = em.getReference(Pasajero.class, id);
                pasajero.getIdPasajero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pasajero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reserva> reservaListOrphanCheck = pasajero.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pasajero (" + pasajero + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable pasajero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona idPersona = pasajero.getIdPersona();
            if (idPersona != null) {
                idPersona.getPasajeroList().remove(pasajero);
                idPersona = em.merge(idPersona);
            }
            Usuario usuarioPas = pasajero.getUsuarioPas();
            if (usuarioPas != null) {
                usuarioPas.getPasajeroList().remove(pasajero);
                usuarioPas = em.merge(usuarioPas);
            }
            em.remove(pasajero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pasajero> findPasajeroEntities() {
        return findPasajeroEntities(true, -1, -1);
    }

    public List<Pasajero> findPasajeroEntities(int maxResults, int firstResult) {
        return findPasajeroEntities(false, maxResults, firstResult);
    }

    private List<Pasajero> findPasajeroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pasajero.class));
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

    public Pasajero findPasajero(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pasajero.class, id);
        } finally {
            em.close();
        }
    }

    public int getPasajeroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pasajero> rt = cq.from(Pasajero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
