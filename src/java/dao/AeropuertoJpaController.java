/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import entities.Aeropuerto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Ciudad;
import entities.Destino;
import java.util.ArrayList;
import java.util.List;
import entities.Origen;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class AeropuertoJpaController implements Serializable {

    public AeropuertoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aeropuerto aeropuerto) throws PreexistingEntityException, Exception {
        if (aeropuerto.getDestinoList() == null) {
            aeropuerto.setDestinoList(new ArrayList<Destino>());
        }
        if (aeropuerto.getOrigenList() == null) {
            aeropuerto.setOrigenList(new ArrayList<Origen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad = aeropuerto.getCiudad();
            if (ciudad != null) {
                ciudad = em.getReference(ciudad.getClass(), ciudad.getCodCiu());
                aeropuerto.setCiudad(ciudad);
            }
            List<Destino> attachedDestinoList = new ArrayList<Destino>();
            for (Destino destinoListDestinoToAttach : aeropuerto.getDestinoList()) {
                destinoListDestinoToAttach = em.getReference(destinoListDestinoToAttach.getClass(), destinoListDestinoToAttach.getDestinoPK());
                attachedDestinoList.add(destinoListDestinoToAttach);
            }
            aeropuerto.setDestinoList(attachedDestinoList);
            List<Origen> attachedOrigenList = new ArrayList<Origen>();
            for (Origen origenListOrigenToAttach : aeropuerto.getOrigenList()) {
                origenListOrigenToAttach = em.getReference(origenListOrigenToAttach.getClass(), origenListOrigenToAttach.getOrigenPK());
                attachedOrigenList.add(origenListOrigenToAttach);
            }
            aeropuerto.setOrigenList(attachedOrigenList);
            em.persist(aeropuerto);
            if (ciudad != null) {
                ciudad.getAeropuertoList().add(aeropuerto);
                ciudad = em.merge(ciudad);
            }
            for (Destino destinoListDestino : aeropuerto.getDestinoList()) {
                Aeropuerto oldAeropuertoOfDestinoListDestino = destinoListDestino.getAeropuerto();
                destinoListDestino.setAeropuerto(aeropuerto);
                destinoListDestino = em.merge(destinoListDestino);
                if (oldAeropuertoOfDestinoListDestino != null) {
                    oldAeropuertoOfDestinoListDestino.getDestinoList().remove(destinoListDestino);
                    oldAeropuertoOfDestinoListDestino = em.merge(oldAeropuertoOfDestinoListDestino);
                }
            }
            for (Origen origenListOrigen : aeropuerto.getOrigenList()) {
                Aeropuerto oldAeropuertoOfOrigenListOrigen = origenListOrigen.getAeropuerto();
                origenListOrigen.setAeropuerto(aeropuerto);
                origenListOrigen = em.merge(origenListOrigen);
                if (oldAeropuertoOfOrigenListOrigen != null) {
                    oldAeropuertoOfOrigenListOrigen.getOrigenList().remove(origenListOrigen);
                    oldAeropuertoOfOrigenListOrigen = em.merge(oldAeropuertoOfOrigenListOrigen);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAeropuerto(aeropuerto.getIata()) != null) {
                throw new PreexistingEntityException("Aeropuerto " + aeropuerto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aeropuerto aeropuerto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aeropuerto persistentAeropuerto = em.find(Aeropuerto.class, aeropuerto.getIata());
            Ciudad ciudadOld = persistentAeropuerto.getCiudad();
            Ciudad ciudadNew = aeropuerto.getCiudad();
            List<Destino> destinoListOld = persistentAeropuerto.getDestinoList();
            List<Destino> destinoListNew = aeropuerto.getDestinoList();
            List<Origen> origenListOld = persistentAeropuerto.getOrigenList();
            List<Origen> origenListNew = aeropuerto.getOrigenList();
            List<String> illegalOrphanMessages = null;
            for (Destino destinoListOldDestino : destinoListOld) {
                if (!destinoListNew.contains(destinoListOldDestino)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Destino " + destinoListOldDestino + " since its aeropuerto field is not nullable.");
                }
            }
            for (Origen origenListOldOrigen : origenListOld) {
                if (!origenListNew.contains(origenListOldOrigen)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Origen " + origenListOldOrigen + " since its aeropuerto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadNew != null) {
                ciudadNew = em.getReference(ciudadNew.getClass(), ciudadNew.getCodCiu());
                aeropuerto.setCiudad(ciudadNew);
            }
            List<Destino> attachedDestinoListNew = new ArrayList<Destino>();
            for (Destino destinoListNewDestinoToAttach : destinoListNew) {
                destinoListNewDestinoToAttach = em.getReference(destinoListNewDestinoToAttach.getClass(), destinoListNewDestinoToAttach.getDestinoPK());
                attachedDestinoListNew.add(destinoListNewDestinoToAttach);
            }
            destinoListNew = attachedDestinoListNew;
            aeropuerto.setDestinoList(destinoListNew);
            List<Origen> attachedOrigenListNew = new ArrayList<Origen>();
            for (Origen origenListNewOrigenToAttach : origenListNew) {
                origenListNewOrigenToAttach = em.getReference(origenListNewOrigenToAttach.getClass(), origenListNewOrigenToAttach.getOrigenPK());
                attachedOrigenListNew.add(origenListNewOrigenToAttach);
            }
            origenListNew = attachedOrigenListNew;
            aeropuerto.setOrigenList(origenListNew);
            aeropuerto = em.merge(aeropuerto);
            if (ciudadOld != null && !ciudadOld.equals(ciudadNew)) {
                ciudadOld.getAeropuertoList().remove(aeropuerto);
                ciudadOld = em.merge(ciudadOld);
            }
            if (ciudadNew != null && !ciudadNew.equals(ciudadOld)) {
                ciudadNew.getAeropuertoList().add(aeropuerto);
                ciudadNew = em.merge(ciudadNew);
            }
            for (Destino destinoListNewDestino : destinoListNew) {
                if (!destinoListOld.contains(destinoListNewDestino)) {
                    Aeropuerto oldAeropuertoOfDestinoListNewDestino = destinoListNewDestino.getAeropuerto();
                    destinoListNewDestino.setAeropuerto(aeropuerto);
                    destinoListNewDestino = em.merge(destinoListNewDestino);
                    if (oldAeropuertoOfDestinoListNewDestino != null && !oldAeropuertoOfDestinoListNewDestino.equals(aeropuerto)) {
                        oldAeropuertoOfDestinoListNewDestino.getDestinoList().remove(destinoListNewDestino);
                        oldAeropuertoOfDestinoListNewDestino = em.merge(oldAeropuertoOfDestinoListNewDestino);
                    }
                }
            }
            for (Origen origenListNewOrigen : origenListNew) {
                if (!origenListOld.contains(origenListNewOrigen)) {
                    Aeropuerto oldAeropuertoOfOrigenListNewOrigen = origenListNewOrigen.getAeropuerto();
                    origenListNewOrigen.setAeropuerto(aeropuerto);
                    origenListNewOrigen = em.merge(origenListNewOrigen);
                    if (oldAeropuertoOfOrigenListNewOrigen != null && !oldAeropuertoOfOrigenListNewOrigen.equals(aeropuerto)) {
                        oldAeropuertoOfOrigenListNewOrigen.getOrigenList().remove(origenListNewOrigen);
                        oldAeropuertoOfOrigenListNewOrigen = em.merge(oldAeropuertoOfOrigenListNewOrigen);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = aeropuerto.getIata();
                if (findAeropuerto(id) == null) {
                    throw new NonexistentEntityException("The aeropuerto with id " + id + " no longer exists.");
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
            Aeropuerto aeropuerto;
            try {
                aeropuerto = em.getReference(Aeropuerto.class, id);
                aeropuerto.getIata();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aeropuerto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Destino> destinoListOrphanCheck = aeropuerto.getDestinoList();
            for (Destino destinoListOrphanCheckDestino : destinoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aeropuerto (" + aeropuerto + ") cannot be destroyed since the Destino " + destinoListOrphanCheckDestino + " in its destinoList field has a non-nullable aeropuerto field.");
            }
            List<Origen> origenListOrphanCheck = aeropuerto.getOrigenList();
            for (Origen origenListOrphanCheckOrigen : origenListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aeropuerto (" + aeropuerto + ") cannot be destroyed since the Origen " + origenListOrphanCheckOrigen + " in its origenList field has a non-nullable aeropuerto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudad = aeropuerto.getCiudad();
            if (ciudad != null) {
                ciudad.getAeropuertoList().remove(aeropuerto);
                ciudad = em.merge(ciudad);
            }
            em.remove(aeropuerto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aeropuerto> findAeropuertoEntities() {
        return findAeropuertoEntities(true, -1, -1);
    }

    public List<Aeropuerto> findAeropuertoEntities(int maxResults, int firstResult) {
        return findAeropuertoEntities(false, maxResults, firstResult);
    }

    private List<Aeropuerto> findAeropuertoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aeropuerto.class));
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

    public Aeropuerto findAeropuerto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aeropuerto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAeropuertoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aeropuerto> rt = cq.from(Aeropuerto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
