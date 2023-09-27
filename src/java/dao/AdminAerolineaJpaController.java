/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import entities.AdminAerolinea;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class AdminAerolineaJpaController implements Serializable {

    public AdminAerolineaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdminAerolinea adminAerolinea) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = adminAerolinea.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                adminAerolinea.setUsuario(usuario);
            }
            em.persist(adminAerolinea);
            if (usuario != null) {
                usuario.getAdminAerolineaList().add(adminAerolinea);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdminAerolinea(adminAerolinea.getId()) != null) {
                throw new PreexistingEntityException("AdminAerolinea " + adminAerolinea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdminAerolinea adminAerolinea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdminAerolinea persistentAdminAerolinea = em.find(AdminAerolinea.class, adminAerolinea.getId());
            Usuario usuarioOld = persistentAdminAerolinea.getUsuario();
            Usuario usuarioNew = adminAerolinea.getUsuario();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                adminAerolinea.setUsuario(usuarioNew);
            }
            adminAerolinea = em.merge(adminAerolinea);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getAdminAerolineaList().remove(adminAerolinea);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getAdminAerolineaList().add(adminAerolinea);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = adminAerolinea.getId();
                if (findAdminAerolinea(id) == null) {
                    throw new NonexistentEntityException("The adminAerolinea with id " + id + " no longer exists.");
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
            AdminAerolinea adminAerolinea;
            try {
                adminAerolinea = em.getReference(AdminAerolinea.class, id);
                adminAerolinea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adminAerolinea with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = adminAerolinea.getUsuario();
            if (usuario != null) {
                usuario.getAdminAerolineaList().remove(adminAerolinea);
                usuario = em.merge(usuario);
            }
            em.remove(adminAerolinea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdminAerolinea> findAdminAerolineaEntities() {
        return findAdminAerolineaEntities(true, -1, -1);
    }

    public List<AdminAerolinea> findAdminAerolineaEntities(int maxResults, int firstResult) {
        return findAdminAerolineaEntities(false, maxResults, firstResult);
    }

    private List<AdminAerolinea> findAdminAerolineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdminAerolinea.class));
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

    public AdminAerolinea findAdminAerolinea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdminAerolinea.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdminAerolineaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdminAerolinea> rt = cq.from(AdminAerolinea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
