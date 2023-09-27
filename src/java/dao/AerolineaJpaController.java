/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import entities.Aerolinea;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Avion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class AerolineaJpaController implements Serializable {

    public AerolineaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aerolinea aerolinea) throws PreexistingEntityException, Exception {
        if (aerolinea.getAvionList() == null) {
            aerolinea.setAvionList(new ArrayList<Avion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Avion> attachedAvionList = new ArrayList<Avion>();
            for (Avion avionListAvionToAttach : aerolinea.getAvionList()) {
                avionListAvionToAttach = em.getReference(avionListAvionToAttach.getClass(), avionListAvionToAttach.getMatricula());
                attachedAvionList.add(avionListAvionToAttach);
            }
            aerolinea.setAvionList(attachedAvionList);
            em.persist(aerolinea);
            for (Avion avionListAvion : aerolinea.getAvionList()) {
                Aerolinea oldAerolineaOfAvionListAvion = avionListAvion.getAerolinea();
                avionListAvion.setAerolinea(aerolinea);
                avionListAvion = em.merge(avionListAvion);
                if (oldAerolineaOfAvionListAvion != null) {
                    oldAerolineaOfAvionListAvion.getAvionList().remove(avionListAvion);
                    oldAerolineaOfAvionListAvion = em.merge(oldAerolineaOfAvionListAvion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAerolinea(aerolinea.getNit()) != null) {
                throw new PreexistingEntityException("Aerolinea " + aerolinea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aerolinea aerolinea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aerolinea persistentAerolinea = em.find(Aerolinea.class, aerolinea.getNit());
            List<Avion> avionListOld = persistentAerolinea.getAvionList();
            List<Avion> avionListNew = aerolinea.getAvionList();
            List<Avion> attachedAvionListNew = new ArrayList<Avion>();
            for (Avion avionListNewAvionToAttach : avionListNew) {
                avionListNewAvionToAttach = em.getReference(avionListNewAvionToAttach.getClass(), avionListNewAvionToAttach.getMatricula());
                attachedAvionListNew.add(avionListNewAvionToAttach);
            }
            avionListNew = attachedAvionListNew;
            aerolinea.setAvionList(avionListNew);
            aerolinea = em.merge(aerolinea);
            for (Avion avionListOldAvion : avionListOld) {
                if (!avionListNew.contains(avionListOldAvion)) {
                    avionListOldAvion.setAerolinea(null);
                    avionListOldAvion = em.merge(avionListOldAvion);
                }
            }
            for (Avion avionListNewAvion : avionListNew) {
                if (!avionListOld.contains(avionListNewAvion)) {
                    Aerolinea oldAerolineaOfAvionListNewAvion = avionListNewAvion.getAerolinea();
                    avionListNewAvion.setAerolinea(aerolinea);
                    avionListNewAvion = em.merge(avionListNewAvion);
                    if (oldAerolineaOfAvionListNewAvion != null && !oldAerolineaOfAvionListNewAvion.equals(aerolinea)) {
                        oldAerolineaOfAvionListNewAvion.getAvionList().remove(avionListNewAvion);
                        oldAerolineaOfAvionListNewAvion = em.merge(oldAerolineaOfAvionListNewAvion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = aerolinea.getNit();
                if (findAerolinea(id) == null) {
                    throw new NonexistentEntityException("The aerolinea with id " + id + " no longer exists.");
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
            Aerolinea aerolinea;
            try {
                aerolinea = em.getReference(Aerolinea.class, id);
                aerolinea.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aerolinea with id " + id + " no longer exists.", enfe);
            }
            List<Avion> avionList = aerolinea.getAvionList();
            for (Avion avionListAvion : avionList) {
                avionListAvion.setAerolinea(null);
                avionListAvion = em.merge(avionListAvion);
            }
            em.remove(aerolinea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aerolinea> findAerolineaEntities() {
        return findAerolineaEntities(true, -1, -1);
    }

    public List<Aerolinea> findAerolineaEntities(int maxResults, int firstResult) {
        return findAerolineaEntities(false, maxResults, firstResult);
    }

    private List<Aerolinea> findAerolineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aerolinea.class));
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

    public Aerolinea findAerolinea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aerolinea.class, id);
        } finally {
            em.close();
        }
    }

    public int getAerolineaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aerolinea> rt = cq.from(Aerolinea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
