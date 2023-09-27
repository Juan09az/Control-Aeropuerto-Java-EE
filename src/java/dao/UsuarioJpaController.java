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
import entities.AdminAerolinea;
import java.util.ArrayList;
import java.util.List;
import entities.Pasajero;
import entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getAdminAerolineaList() == null) {
            usuario.setAdminAerolineaList(new ArrayList<AdminAerolinea>());
        }
        if (usuario.getPasajeroList() == null) {
            usuario.setPasajeroList(new ArrayList<Pasajero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AdminAerolinea> attachedAdminAerolineaList = new ArrayList<AdminAerolinea>();
            for (AdminAerolinea adminAerolineaListAdminAerolineaToAttach : usuario.getAdminAerolineaList()) {
                adminAerolineaListAdminAerolineaToAttach = em.getReference(adminAerolineaListAdminAerolineaToAttach.getClass(), adminAerolineaListAdminAerolineaToAttach.getId());
                attachedAdminAerolineaList.add(adminAerolineaListAdminAerolineaToAttach);
            }
            usuario.setAdminAerolineaList(attachedAdminAerolineaList);
            List<Pasajero> attachedPasajeroList = new ArrayList<Pasajero>();
            for (Pasajero pasajeroListPasajeroToAttach : usuario.getPasajeroList()) {
                pasajeroListPasajeroToAttach = em.getReference(pasajeroListPasajeroToAttach.getClass(), pasajeroListPasajeroToAttach.getIdPasajero());
                attachedPasajeroList.add(pasajeroListPasajeroToAttach);
            }
            usuario.setPasajeroList(attachedPasajeroList);
            em.persist(usuario);
            for (AdminAerolinea adminAerolineaListAdminAerolinea : usuario.getAdminAerolineaList()) {
                Usuario oldUsuarioOfAdminAerolineaListAdminAerolinea = adminAerolineaListAdminAerolinea.getUsuario();
                adminAerolineaListAdminAerolinea.setUsuario(usuario);
                adminAerolineaListAdminAerolinea = em.merge(adminAerolineaListAdminAerolinea);
                if (oldUsuarioOfAdminAerolineaListAdminAerolinea != null) {
                    oldUsuarioOfAdminAerolineaListAdminAerolinea.getAdminAerolineaList().remove(adminAerolineaListAdminAerolinea);
                    oldUsuarioOfAdminAerolineaListAdminAerolinea = em.merge(oldUsuarioOfAdminAerolineaListAdminAerolinea);
                }
            }
            for (Pasajero pasajeroListPasajero : usuario.getPasajeroList()) {
                Usuario oldUsuarioPasOfPasajeroListPasajero = pasajeroListPasajero.getUsuarioPas();
                pasajeroListPasajero.setUsuarioPas(usuario);
                pasajeroListPasajero = em.merge(pasajeroListPasajero);
                if (oldUsuarioPasOfPasajeroListPasajero != null) {
                    oldUsuarioPasOfPasajeroListPasajero.getPasajeroList().remove(pasajeroListPasajero);
                    oldUsuarioPasOfPasajeroListPasajero = em.merge(oldUsuarioPasOfPasajeroListPasajero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            List<AdminAerolinea> adminAerolineaListOld = persistentUsuario.getAdminAerolineaList();
            List<AdminAerolinea> adminAerolineaListNew = usuario.getAdminAerolineaList();
            List<Pasajero> pasajeroListOld = persistentUsuario.getPasajeroList();
            List<Pasajero> pasajeroListNew = usuario.getPasajeroList();
            List<AdminAerolinea> attachedAdminAerolineaListNew = new ArrayList<AdminAerolinea>();
            for (AdminAerolinea adminAerolineaListNewAdminAerolineaToAttach : adminAerolineaListNew) {
                adminAerolineaListNewAdminAerolineaToAttach = em.getReference(adminAerolineaListNewAdminAerolineaToAttach.getClass(), adminAerolineaListNewAdminAerolineaToAttach.getId());
                attachedAdminAerolineaListNew.add(adminAerolineaListNewAdminAerolineaToAttach);
            }
            adminAerolineaListNew = attachedAdminAerolineaListNew;
            usuario.setAdminAerolineaList(adminAerolineaListNew);
            List<Pasajero> attachedPasajeroListNew = new ArrayList<Pasajero>();
            for (Pasajero pasajeroListNewPasajeroToAttach : pasajeroListNew) {
                pasajeroListNewPasajeroToAttach = em.getReference(pasajeroListNewPasajeroToAttach.getClass(), pasajeroListNewPasajeroToAttach.getIdPasajero());
                attachedPasajeroListNew.add(pasajeroListNewPasajeroToAttach);
            }
            pasajeroListNew = attachedPasajeroListNew;
            usuario.setPasajeroList(pasajeroListNew);
            usuario = em.merge(usuario);
            for (AdminAerolinea adminAerolineaListOldAdminAerolinea : adminAerolineaListOld) {
                if (!adminAerolineaListNew.contains(adminAerolineaListOldAdminAerolinea)) {
                    adminAerolineaListOldAdminAerolinea.setUsuario(null);
                    adminAerolineaListOldAdminAerolinea = em.merge(adminAerolineaListOldAdminAerolinea);
                }
            }
            for (AdminAerolinea adminAerolineaListNewAdminAerolinea : adminAerolineaListNew) {
                if (!adminAerolineaListOld.contains(adminAerolineaListNewAdminAerolinea)) {
                    Usuario oldUsuarioOfAdminAerolineaListNewAdminAerolinea = adminAerolineaListNewAdminAerolinea.getUsuario();
                    adminAerolineaListNewAdminAerolinea.setUsuario(usuario);
                    adminAerolineaListNewAdminAerolinea = em.merge(adminAerolineaListNewAdminAerolinea);
                    if (oldUsuarioOfAdminAerolineaListNewAdminAerolinea != null && !oldUsuarioOfAdminAerolineaListNewAdminAerolinea.equals(usuario)) {
                        oldUsuarioOfAdminAerolineaListNewAdminAerolinea.getAdminAerolineaList().remove(adminAerolineaListNewAdminAerolinea);
                        oldUsuarioOfAdminAerolineaListNewAdminAerolinea = em.merge(oldUsuarioOfAdminAerolineaListNewAdminAerolinea);
                    }
                }
            }
            for (Pasajero pasajeroListOldPasajero : pasajeroListOld) {
                if (!pasajeroListNew.contains(pasajeroListOldPasajero)) {
                    pasajeroListOldPasajero.setUsuarioPas(null);
                    pasajeroListOldPasajero = em.merge(pasajeroListOldPasajero);
                }
            }
            for (Pasajero pasajeroListNewPasajero : pasajeroListNew) {
                if (!pasajeroListOld.contains(pasajeroListNewPasajero)) {
                    Usuario oldUsuarioPasOfPasajeroListNewPasajero = pasajeroListNewPasajero.getUsuarioPas();
                    pasajeroListNewPasajero.setUsuarioPas(usuario);
                    pasajeroListNewPasajero = em.merge(pasajeroListNewPasajero);
                    if (oldUsuarioPasOfPasajeroListNewPasajero != null && !oldUsuarioPasOfPasajeroListNewPasajero.equals(usuario)) {
                        oldUsuarioPasOfPasajeroListNewPasajero.getPasajeroList().remove(pasajeroListNewPasajero);
                        oldUsuarioPasOfPasajeroListNewPasajero = em.merge(oldUsuarioPasOfPasajeroListNewPasajero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<AdminAerolinea> adminAerolineaList = usuario.getAdminAerolineaList();
            for (AdminAerolinea adminAerolineaListAdminAerolinea : adminAerolineaList) {
                adminAerolineaListAdminAerolinea.setUsuario(null);
                adminAerolineaListAdminAerolinea = em.merge(adminAerolineaListAdminAerolinea);
            }
            List<Pasajero> pasajeroList = usuario.getPasajeroList();
            for (Pasajero pasajeroListPasajero : pasajeroList) {
                pasajeroListPasajero.setUsuarioPas(null);
                pasajeroListPasajero = em.merge(pasajeroListPasajero);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
