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
import entities.Ciudad;
import entities.Pais;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, Exception {
        if (pais.getCiudadList() == null) {
            pais.setCiudadList(new ArrayList<Ciudad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ciudad> attachedCiudadList = new ArrayList<Ciudad>();
            for (Ciudad ciudadListCiudadToAttach : pais.getCiudadList()) {
                ciudadListCiudadToAttach = em.getReference(ciudadListCiudadToAttach.getClass(), ciudadListCiudadToAttach.getCodCiu());
                attachedCiudadList.add(ciudadListCiudadToAttach);
            }
            pais.setCiudadList(attachedCiudadList);
            em.persist(pais);
            for (Ciudad ciudadListCiudad : pais.getCiudadList()) {
                Pais oldCodPaisOfCiudadListCiudad = ciudadListCiudad.getCodPais();
                ciudadListCiudad.setCodPais(pais);
                ciudadListCiudad = em.merge(ciudadListCiudad);
                if (oldCodPaisOfCiudadListCiudad != null) {
                    oldCodPaisOfCiudadListCiudad.getCiudadList().remove(ciudadListCiudad);
                    oldCodPaisOfCiudadListCiudad = em.merge(oldCodPaisOfCiudadListCiudad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPais(pais.getCodPais()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getCodPais());
            List<Ciudad> ciudadListOld = persistentPais.getCiudadList();
            List<Ciudad> ciudadListNew = pais.getCiudadList();
            List<Ciudad> attachedCiudadListNew = new ArrayList<Ciudad>();
            for (Ciudad ciudadListNewCiudadToAttach : ciudadListNew) {
                ciudadListNewCiudadToAttach = em.getReference(ciudadListNewCiudadToAttach.getClass(), ciudadListNewCiudadToAttach.getCodCiu());
                attachedCiudadListNew.add(ciudadListNewCiudadToAttach);
            }
            ciudadListNew = attachedCiudadListNew;
            pais.setCiudadList(ciudadListNew);
            pais = em.merge(pais);
            for (Ciudad ciudadListOldCiudad : ciudadListOld) {
                if (!ciudadListNew.contains(ciudadListOldCiudad)) {
                    ciudadListOldCiudad.setCodPais(null);
                    ciudadListOldCiudad = em.merge(ciudadListOldCiudad);
                }
            }
            for (Ciudad ciudadListNewCiudad : ciudadListNew) {
                if (!ciudadListOld.contains(ciudadListNewCiudad)) {
                    Pais oldCodPaisOfCiudadListNewCiudad = ciudadListNewCiudad.getCodPais();
                    ciudadListNewCiudad.setCodPais(pais);
                    ciudadListNewCiudad = em.merge(ciudadListNewCiudad);
                    if (oldCodPaisOfCiudadListNewCiudad != null && !oldCodPaisOfCiudadListNewCiudad.equals(pais)) {
                        oldCodPaisOfCiudadListNewCiudad.getCiudadList().remove(ciudadListNewCiudad);
                        oldCodPaisOfCiudadListNewCiudad = em.merge(oldCodPaisOfCiudadListNewCiudad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pais.getCodPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getCodPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<Ciudad> ciudadList = pais.getCiudadList();
            for (Ciudad ciudadListCiudad : ciudadList) {
                ciudadListCiudad.setCodPais(null);
                ciudadListCiudad = em.merge(ciudadListCiudad);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
