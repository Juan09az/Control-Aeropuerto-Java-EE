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
import entities.Pais;
import entities.Aeropuerto;
import entities.Ciudad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author camil
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws PreexistingEntityException, Exception {
        if (ciudad.getAeropuertoList() == null) {
            ciudad.setAeropuertoList(new ArrayList<Aeropuerto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais codPais = ciudad.getCodPais();
            if (codPais != null) {
                codPais = em.getReference(codPais.getClass(), codPais.getCodPais());
                ciudad.setCodPais(codPais);
            }
            List<Aeropuerto> attachedAeropuertoList = new ArrayList<Aeropuerto>();
            for (Aeropuerto aeropuertoListAeropuertoToAttach : ciudad.getAeropuertoList()) {
                aeropuertoListAeropuertoToAttach = em.getReference(aeropuertoListAeropuertoToAttach.getClass(), aeropuertoListAeropuertoToAttach.getIata());
                attachedAeropuertoList.add(aeropuertoListAeropuertoToAttach);
            }
            ciudad.setAeropuertoList(attachedAeropuertoList);
            em.persist(ciudad);
            if (codPais != null) {
                codPais.getCiudadList().add(ciudad);
                codPais = em.merge(codPais);
            }
            for (Aeropuerto aeropuertoListAeropuerto : ciudad.getAeropuertoList()) {
                Ciudad oldCiudadOfAeropuertoListAeropuerto = aeropuertoListAeropuerto.getCiudad();
                aeropuertoListAeropuerto.setCiudad(ciudad);
                aeropuertoListAeropuerto = em.merge(aeropuertoListAeropuerto);
                if (oldCiudadOfAeropuertoListAeropuerto != null) {
                    oldCiudadOfAeropuertoListAeropuerto.getAeropuertoList().remove(aeropuertoListAeropuerto);
                    oldCiudadOfAeropuertoListAeropuerto = em.merge(oldCiudadOfAeropuertoListAeropuerto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCiudad(ciudad.getCodCiu()) != null) {
                throw new PreexistingEntityException("Ciudad " + ciudad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getCodCiu());
            Pais codPaisOld = persistentCiudad.getCodPais();
            Pais codPaisNew = ciudad.getCodPais();
            List<Aeropuerto> aeropuertoListOld = persistentCiudad.getAeropuertoList();
            List<Aeropuerto> aeropuertoListNew = ciudad.getAeropuertoList();
            if (codPaisNew != null) {
                codPaisNew = em.getReference(codPaisNew.getClass(), codPaisNew.getCodPais());
                ciudad.setCodPais(codPaisNew);
            }
            List<Aeropuerto> attachedAeropuertoListNew = new ArrayList<Aeropuerto>();
            for (Aeropuerto aeropuertoListNewAeropuertoToAttach : aeropuertoListNew) {
                aeropuertoListNewAeropuertoToAttach = em.getReference(aeropuertoListNewAeropuertoToAttach.getClass(), aeropuertoListNewAeropuertoToAttach.getIata());
                attachedAeropuertoListNew.add(aeropuertoListNewAeropuertoToAttach);
            }
            aeropuertoListNew = attachedAeropuertoListNew;
            ciudad.setAeropuertoList(aeropuertoListNew);
            ciudad = em.merge(ciudad);
            if (codPaisOld != null && !codPaisOld.equals(codPaisNew)) {
                codPaisOld.getCiudadList().remove(ciudad);
                codPaisOld = em.merge(codPaisOld);
            }
            if (codPaisNew != null && !codPaisNew.equals(codPaisOld)) {
                codPaisNew.getCiudadList().add(ciudad);
                codPaisNew = em.merge(codPaisNew);
            }
            for (Aeropuerto aeropuertoListOldAeropuerto : aeropuertoListOld) {
                if (!aeropuertoListNew.contains(aeropuertoListOldAeropuerto)) {
                    aeropuertoListOldAeropuerto.setCiudad(null);
                    aeropuertoListOldAeropuerto = em.merge(aeropuertoListOldAeropuerto);
                }
            }
            for (Aeropuerto aeropuertoListNewAeropuerto : aeropuertoListNew) {
                if (!aeropuertoListOld.contains(aeropuertoListNewAeropuerto)) {
                    Ciudad oldCiudadOfAeropuertoListNewAeropuerto = aeropuertoListNewAeropuerto.getCiudad();
                    aeropuertoListNewAeropuerto.setCiudad(ciudad);
                    aeropuertoListNewAeropuerto = em.merge(aeropuertoListNewAeropuerto);
                    if (oldCiudadOfAeropuertoListNewAeropuerto != null && !oldCiudadOfAeropuertoListNewAeropuerto.equals(ciudad)) {
                        oldCiudadOfAeropuertoListNewAeropuerto.getAeropuertoList().remove(aeropuertoListNewAeropuerto);
                        oldCiudadOfAeropuertoListNewAeropuerto = em.merge(oldCiudadOfAeropuertoListNewAeropuerto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ciudad.getCodCiu();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getCodCiu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            Pais codPais = ciudad.getCodPais();
            if (codPais != null) {
                codPais.getCiudadList().remove(ciudad);
                codPais = em.merge(codPais);
            }
            List<Aeropuerto> aeropuertoList = ciudad.getAeropuertoList();
            for (Aeropuerto aeropuertoListAeropuerto : aeropuertoList) {
                aeropuertoListAeropuerto.setCiudad(null);
                aeropuertoListAeropuerto = em.merge(aeropuertoListAeropuerto);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
