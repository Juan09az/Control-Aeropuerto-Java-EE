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
import entities.Pasajero;
import entities.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        if (persona.getPasajeroList() == null) {
            persona.setPasajeroList(new ArrayList<Pasajero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pasajero> attachedPasajeroList = new ArrayList<Pasajero>();
            for (Pasajero pasajeroListPasajeroToAttach : persona.getPasajeroList()) {
                pasajeroListPasajeroToAttach = em.getReference(pasajeroListPasajeroToAttach.getClass(), pasajeroListPasajeroToAttach.getIdPasajero());
                attachedPasajeroList.add(pasajeroListPasajeroToAttach);
            }
            persona.setPasajeroList(attachedPasajeroList);
            em.persist(persona);
            for (Pasajero pasajeroListPasajero : persona.getPasajeroList()) {
                Persona oldIdPersonaOfPasajeroListPasajero = pasajeroListPasajero.getIdPersona();
                pasajeroListPasajero.setIdPersona(persona);
                pasajeroListPasajero = em.merge(pasajeroListPasajero);
                if (oldIdPersonaOfPasajeroListPasajero != null) {
                    oldIdPersonaOfPasajeroListPasajero.getPasajeroList().remove(pasajeroListPasajero);
                    oldIdPersonaOfPasajeroListPasajero = em.merge(oldIdPersonaOfPasajeroListPasajero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getIdPersona()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            List<Pasajero> pasajeroListOld = persistentPersona.getPasajeroList();
            List<Pasajero> pasajeroListNew = persona.getPasajeroList();
            List<Pasajero> attachedPasajeroListNew = new ArrayList<Pasajero>();
            for (Pasajero pasajeroListNewPasajeroToAttach : pasajeroListNew) {
                pasajeroListNewPasajeroToAttach = em.getReference(pasajeroListNewPasajeroToAttach.getClass(), pasajeroListNewPasajeroToAttach.getIdPasajero());
                attachedPasajeroListNew.add(pasajeroListNewPasajeroToAttach);
            }
            pasajeroListNew = attachedPasajeroListNew;
            persona.setPasajeroList(pasajeroListNew);
            persona = em.merge(persona);
            for (Pasajero pasajeroListOldPasajero : pasajeroListOld) {
                if (!pasajeroListNew.contains(pasajeroListOldPasajero)) {
                    pasajeroListOldPasajero.setIdPersona(null);
                    pasajeroListOldPasajero = em.merge(pasajeroListOldPasajero);
                }
            }
            for (Pasajero pasajeroListNewPasajero : pasajeroListNew) {
                if (!pasajeroListOld.contains(pasajeroListNewPasajero)) {
                    Persona oldIdPersonaOfPasajeroListNewPasajero = pasajeroListNewPasajero.getIdPersona();
                    pasajeroListNewPasajero.setIdPersona(persona);
                    pasajeroListNewPasajero = em.merge(pasajeroListNewPasajero);
                    if (oldIdPersonaOfPasajeroListNewPasajero != null && !oldIdPersonaOfPasajeroListNewPasajero.equals(persona)) {
                        oldIdPersonaOfPasajeroListNewPasajero.getPasajeroList().remove(pasajeroListNewPasajero);
                        oldIdPersonaOfPasajeroListNewPasajero = em.merge(oldIdPersonaOfPasajeroListNewPasajero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<Pasajero> pasajeroList = persona.getPasajeroList();
            for (Pasajero pasajeroListPasajero : pasajeroList) {
                pasajeroListPasajero.setIdPersona(null);
                pasajeroListPasajero = em.merge(pasajeroListPasajero);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
