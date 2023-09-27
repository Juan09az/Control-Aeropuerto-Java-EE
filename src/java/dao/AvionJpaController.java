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
import entities.Aerolinea;
import entities.Avion;
import entities.Vuelo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class AvionJpaController implements Serializable {

    public AvionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avion avion) throws PreexistingEntityException, Exception {
        if (avion.getVueloList() == null) {
            avion.setVueloList(new ArrayList<Vuelo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aerolinea aerolinea = avion.getAerolinea();
            if (aerolinea != null) {
                aerolinea = em.getReference(aerolinea.getClass(), aerolinea.getNit());
                avion.setAerolinea(aerolinea);
            }
            List<Vuelo> attachedVueloList = new ArrayList<Vuelo>();
            for (Vuelo vueloListVueloToAttach : avion.getVueloList()) {
                vueloListVueloToAttach = em.getReference(vueloListVueloToAttach.getClass(), vueloListVueloToAttach.getCodVuelo());
                attachedVueloList.add(vueloListVueloToAttach);
            }
            avion.setVueloList(attachedVueloList);
            em.persist(avion);
            if (aerolinea != null) {
                aerolinea.getAvionList().add(avion);
                aerolinea = em.merge(aerolinea);
            }
            for (Vuelo vueloListVuelo : avion.getVueloList()) {
                Avion oldAvionOfVueloListVuelo = vueloListVuelo.getAvion();
                vueloListVuelo.setAvion(avion);
                vueloListVuelo = em.merge(vueloListVuelo);
                if (oldAvionOfVueloListVuelo != null) {
                    oldAvionOfVueloListVuelo.getVueloList().remove(vueloListVuelo);
                    oldAvionOfVueloListVuelo = em.merge(oldAvionOfVueloListVuelo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvion(avion.getMatricula()) != null) {
                throw new PreexistingEntityException("Avion " + avion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avion avion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avion persistentAvion = em.find(Avion.class, avion.getMatricula());
            Aerolinea aerolineaOld = persistentAvion.getAerolinea();
            Aerolinea aerolineaNew = avion.getAerolinea();
            List<Vuelo> vueloListOld = persistentAvion.getVueloList();
            List<Vuelo> vueloListNew = avion.getVueloList();
            if (aerolineaNew != null) {
                aerolineaNew = em.getReference(aerolineaNew.getClass(), aerolineaNew.getNit());
                avion.setAerolinea(aerolineaNew);
            }
            List<Vuelo> attachedVueloListNew = new ArrayList<Vuelo>();
            for (Vuelo vueloListNewVueloToAttach : vueloListNew) {
                vueloListNewVueloToAttach = em.getReference(vueloListNewVueloToAttach.getClass(), vueloListNewVueloToAttach.getCodVuelo());
                attachedVueloListNew.add(vueloListNewVueloToAttach);
            }
            vueloListNew = attachedVueloListNew;
            avion.setVueloList(vueloListNew);
            avion = em.merge(avion);
            if (aerolineaOld != null && !aerolineaOld.equals(aerolineaNew)) {
                aerolineaOld.getAvionList().remove(avion);
                aerolineaOld = em.merge(aerolineaOld);
            }
            if (aerolineaNew != null && !aerolineaNew.equals(aerolineaOld)) {
                aerolineaNew.getAvionList().add(avion);
                aerolineaNew = em.merge(aerolineaNew);
            }
            for (Vuelo vueloListOldVuelo : vueloListOld) {
                if (!vueloListNew.contains(vueloListOldVuelo)) {
                    vueloListOldVuelo.setAvion(null);
                    vueloListOldVuelo = em.merge(vueloListOldVuelo);
                }
            }
            for (Vuelo vueloListNewVuelo : vueloListNew) {
                if (!vueloListOld.contains(vueloListNewVuelo)) {
                    Avion oldAvionOfVueloListNewVuelo = vueloListNewVuelo.getAvion();
                    vueloListNewVuelo.setAvion(avion);
                    vueloListNewVuelo = em.merge(vueloListNewVuelo);
                    if (oldAvionOfVueloListNewVuelo != null && !oldAvionOfVueloListNewVuelo.equals(avion)) {
                        oldAvionOfVueloListNewVuelo.getVueloList().remove(vueloListNewVuelo);
                        oldAvionOfVueloListNewVuelo = em.merge(oldAvionOfVueloListNewVuelo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = avion.getMatricula();
                if (findAvion(id) == null) {
                    throw new NonexistentEntityException("The avion with id " + id + " no longer exists.");
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
            Avion avion;
            try {
                avion = em.getReference(Avion.class, id);
                avion.getMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avion with id " + id + " no longer exists.", enfe);
            }
            Aerolinea aerolinea = avion.getAerolinea();
            if (aerolinea != null) {
                aerolinea.getAvionList().remove(avion);
                aerolinea = em.merge(aerolinea);
            }
            List<Vuelo> vueloList = avion.getVueloList();
            for (Vuelo vueloListVuelo : vueloList) {
                vueloListVuelo.setAvion(null);
                vueloListVuelo = em.merge(vueloListVuelo);
            }
            em.remove(avion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avion> findAvionEntities() {
        return findAvionEntities(true, -1, -1);
    }

    public List<Avion> findAvionEntities(int maxResults, int firstResult) {
        return findAvionEntities(false, maxResults, firstResult);
    }

    private List<Avion> findAvionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avion.class));
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

    public Avion findAvion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avion> rt = cq.from(Avion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
