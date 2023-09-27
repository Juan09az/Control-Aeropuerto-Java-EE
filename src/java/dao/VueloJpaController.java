/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Avion;
import entities.Destino;
import java.util.ArrayList;
import java.util.List;
import entities.Reserva;
import entities.Origen;
import entities.Vuelo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class VueloJpaController implements Serializable {

    public VueloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vuelo vuelo) throws PreexistingEntityException, Exception {
        if (vuelo.getDestinoList() == null) {
            vuelo.setDestinoList(new ArrayList<Destino>());
        }
        if (vuelo.getReservaList() == null) {
            vuelo.setReservaList(new ArrayList<Reserva>());
        }
        if (vuelo.getOrigenList() == null) {
            vuelo.setOrigenList(new ArrayList<Origen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avion avion = vuelo.getAvion();
            if (avion != null) {
                avion = em.getReference(avion.getClass(), avion.getMatricula());
                vuelo.setAvion(avion);
            }
            List<Destino> attachedDestinoList = new ArrayList<Destino>();
            for (Destino destinoListDestinoToAttach : vuelo.getDestinoList()) {
                destinoListDestinoToAttach = em.getReference(destinoListDestinoToAttach.getClass(), destinoListDestinoToAttach.getDestinoPK());
                attachedDestinoList.add(destinoListDestinoToAttach);
            }
            vuelo.setDestinoList(attachedDestinoList);
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : vuelo.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getReservaPK());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            vuelo.setReservaList(attachedReservaList);
            List<Origen> attachedOrigenList = new ArrayList<Origen>();
            for (Origen origenListOrigenToAttach : vuelo.getOrigenList()) {
                origenListOrigenToAttach = em.getReference(origenListOrigenToAttach.getClass(), origenListOrigenToAttach.getOrigenPK());
                attachedOrigenList.add(origenListOrigenToAttach);
            }
            vuelo.setOrigenList(attachedOrigenList);
            em.persist(vuelo);
            if (avion != null) {
                avion.getVueloList().add(vuelo);
                avion = em.merge(avion);
            }
            for (Destino destinoListDestino : vuelo.getDestinoList()) {
                Vuelo oldVueloOfDestinoListDestino = destinoListDestino.getVuelo();
                destinoListDestino.setVuelo(vuelo);
                destinoListDestino = em.merge(destinoListDestino);
                if (oldVueloOfDestinoListDestino != null) {
                    oldVueloOfDestinoListDestino.getDestinoList().remove(destinoListDestino);
                    oldVueloOfDestinoListDestino = em.merge(oldVueloOfDestinoListDestino);
                }
            }
            for (Reserva reservaListReserva : vuelo.getReservaList()) {
                Vuelo oldVueloOfReservaListReserva = reservaListReserva.getVuelo();
                reservaListReserva.setVuelo(vuelo);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldVueloOfReservaListReserva != null) {
                    oldVueloOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldVueloOfReservaListReserva = em.merge(oldVueloOfReservaListReserva);
                }
            }
            for (Origen origenListOrigen : vuelo.getOrigenList()) {
                Vuelo oldVueloOfOrigenListOrigen = origenListOrigen.getVuelo();
                origenListOrigen.setVuelo(vuelo);
                origenListOrigen = em.merge(origenListOrigen);
                if (oldVueloOfOrigenListOrigen != null) {
                    oldVueloOfOrigenListOrigen.getOrigenList().remove(origenListOrigen);
                    oldVueloOfOrigenListOrigen = em.merge(oldVueloOfOrigenListOrigen);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVuelo(vuelo.getCodVuelo()) != null) {
                throw new PreexistingEntityException("Vuelo " + vuelo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vuelo vuelo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelo persistentVuelo = em.find(Vuelo.class, vuelo.getCodVuelo());
            Avion avionOld = persistentVuelo.getAvion();
            Avion avionNew = vuelo.getAvion();
            List<Destino> destinoListOld = persistentVuelo.getDestinoList();
            List<Destino> destinoListNew = vuelo.getDestinoList();
            List<Reserva> reservaListOld = persistentVuelo.getReservaList();
            List<Reserva> reservaListNew = vuelo.getReservaList();
            List<Origen> origenListOld = persistentVuelo.getOrigenList();
            List<Origen> origenListNew = vuelo.getOrigenList();
            List<String> illegalOrphanMessages = null;
            for (Destino destinoListOldDestino : destinoListOld) {
                if (!destinoListNew.contains(destinoListOldDestino)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Destino " + destinoListOldDestino + " since its vuelo field is not nullable.");
                }
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its vuelo field is not nullable.");
                }
            }
            for (Origen origenListOldOrigen : origenListOld) {
                if (!origenListNew.contains(origenListOldOrigen)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Origen " + origenListOldOrigen + " since its vuelo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (avionNew != null) {
                avionNew = em.getReference(avionNew.getClass(), avionNew.getMatricula());
                vuelo.setAvion(avionNew);
            }
            List<Destino> attachedDestinoListNew = new ArrayList<Destino>();
            for (Destino destinoListNewDestinoToAttach : destinoListNew) {
                destinoListNewDestinoToAttach = em.getReference(destinoListNewDestinoToAttach.getClass(), destinoListNewDestinoToAttach.getDestinoPK());
                attachedDestinoListNew.add(destinoListNewDestinoToAttach);
            }
            destinoListNew = attachedDestinoListNew;
            vuelo.setDestinoList(destinoListNew);
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getReservaPK());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            vuelo.setReservaList(reservaListNew);
            List<Origen> attachedOrigenListNew = new ArrayList<Origen>();
            for (Origen origenListNewOrigenToAttach : origenListNew) {
                origenListNewOrigenToAttach = em.getReference(origenListNewOrigenToAttach.getClass(), origenListNewOrigenToAttach.getOrigenPK());
                attachedOrigenListNew.add(origenListNewOrigenToAttach);
            }
            origenListNew = attachedOrigenListNew;
            vuelo.setOrigenList(origenListNew);
            vuelo = em.merge(vuelo);
            if (avionOld != null && !avionOld.equals(avionNew)) {
                avionOld.getVueloList().remove(vuelo);
                avionOld = em.merge(avionOld);
            }
            if (avionNew != null && !avionNew.equals(avionOld)) {
                avionNew.getVueloList().add(vuelo);
                avionNew = em.merge(avionNew);
            }
            for (Destino destinoListNewDestino : destinoListNew) {
                if (!destinoListOld.contains(destinoListNewDestino)) {
                    Vuelo oldVueloOfDestinoListNewDestino = destinoListNewDestino.getVuelo();
                    destinoListNewDestino.setVuelo(vuelo);
                    destinoListNewDestino = em.merge(destinoListNewDestino);
                    if (oldVueloOfDestinoListNewDestino != null && !oldVueloOfDestinoListNewDestino.equals(vuelo)) {
                        oldVueloOfDestinoListNewDestino.getDestinoList().remove(destinoListNewDestino);
                        oldVueloOfDestinoListNewDestino = em.merge(oldVueloOfDestinoListNewDestino);
                    }
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Vuelo oldVueloOfReservaListNewReserva = reservaListNewReserva.getVuelo();
                    reservaListNewReserva.setVuelo(vuelo);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldVueloOfReservaListNewReserva != null && !oldVueloOfReservaListNewReserva.equals(vuelo)) {
                        oldVueloOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldVueloOfReservaListNewReserva = em.merge(oldVueloOfReservaListNewReserva);
                    }
                }
            }
            for (Origen origenListNewOrigen : origenListNew) {
                if (!origenListOld.contains(origenListNewOrigen)) {
                    Vuelo oldVueloOfOrigenListNewOrigen = origenListNewOrigen.getVuelo();
                    origenListNewOrigen.setVuelo(vuelo);
                    origenListNewOrigen = em.merge(origenListNewOrigen);
                    if (oldVueloOfOrigenListNewOrigen != null && !oldVueloOfOrigenListNewOrigen.equals(vuelo)) {
                        oldVueloOfOrigenListNewOrigen.getOrigenList().remove(origenListNewOrigen);
                        oldVueloOfOrigenListNewOrigen = em.merge(oldVueloOfOrigenListNewOrigen);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vuelo.getCodVuelo();
                if (findVuelo(id) == null) {
                    throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.");
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
            Vuelo vuelo;
            try {
                vuelo = em.getReference(Vuelo.class, id);
                vuelo.getCodVuelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Destino> destinoListOrphanCheck = vuelo.getDestinoList();
            for (Destino destinoListOrphanCheckDestino : destinoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Destino " + destinoListOrphanCheckDestino + " in its destinoList field has a non-nullable vuelo field.");
            }
            List<Reserva> reservaListOrphanCheck = vuelo.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable vuelo field.");
            }
            List<Origen> origenListOrphanCheck = vuelo.getOrigenList();
            for (Origen origenListOrphanCheckOrigen : origenListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelo (" + vuelo + ") cannot be destroyed since the Origen " + origenListOrphanCheckOrigen + " in its origenList field has a non-nullable vuelo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Avion avion = vuelo.getAvion();
            if (avion != null) {
                avion.getVueloList().remove(vuelo);
                avion = em.merge(avion);
            }
            em.remove(vuelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vuelo> findVueloEntities() {
        return findVueloEntities(true, -1, -1);
    }

    public List<Vuelo> findVueloEntities(int maxResults, int firstResult) {
        return findVueloEntities(false, maxResults, firstResult);
    }

    private List<Vuelo> findVueloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vuelo.class));
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

    public Vuelo findVuelo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vuelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVueloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vuelo> rt = cq.from(Vuelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
