/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Modelo.Estudiante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.MateriaEstudiante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class EstudianteDAO implements Serializable {
    
    public EstudianteDAO() {
        this.emf=Persistence.createEntityManagerFactory("ExamenFinal_-_Sistema_de_NotasPU");
    }

    public EstudianteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) throws PreexistingEntityException, Exception {
        if (estudiante.getMateriaEstudianteList() == null) {
            estudiante.setMateriaEstudianteList(new ArrayList<MateriaEstudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MateriaEstudiante> attachedMateriaEstudianteList = new ArrayList<MateriaEstudiante>();
            for (MateriaEstudiante materiaEstudianteListMateriaEstudianteToAttach : estudiante.getMateriaEstudianteList()) {
                materiaEstudianteListMateriaEstudianteToAttach = em.getReference(materiaEstudianteListMateriaEstudianteToAttach.getClass(), materiaEstudianteListMateriaEstudianteToAttach.getMateriaEstudiantePK());
                attachedMateriaEstudianteList.add(materiaEstudianteListMateriaEstudianteToAttach);
            }
            estudiante.setMateriaEstudianteList(attachedMateriaEstudianteList);
            em.persist(estudiante);
            for (MateriaEstudiante materiaEstudianteListMateriaEstudiante : estudiante.getMateriaEstudianteList()) {
                Estudiante oldEstudianteOfMateriaEstudianteListMateriaEstudiante = materiaEstudianteListMateriaEstudiante.getEstudiante();
                materiaEstudianteListMateriaEstudiante.setEstudiante(estudiante);
                materiaEstudianteListMateriaEstudiante = em.merge(materiaEstudianteListMateriaEstudiante);
                if (oldEstudianteOfMateriaEstudianteListMateriaEstudiante != null) {
                    oldEstudianteOfMateriaEstudianteListMateriaEstudiante.getMateriaEstudianteList().remove(materiaEstudianteListMateriaEstudiante);
                    oldEstudianteOfMateriaEstudianteListMateriaEstudiante = em.merge(oldEstudianteOfMateriaEstudianteListMateriaEstudiante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante(estudiante.getCodigo()) != null) {
                throw new PreexistingEntityException("Estudiante " + estudiante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getCodigo());
            List<MateriaEstudiante> materiaEstudianteListOld = persistentEstudiante.getMateriaEstudianteList();
            List<MateriaEstudiante> materiaEstudianteListNew = estudiante.getMateriaEstudianteList();
            List<String> illegalOrphanMessages = null;
            for (MateriaEstudiante materiaEstudianteListOldMateriaEstudiante : materiaEstudianteListOld) {
                if (!materiaEstudianteListNew.contains(materiaEstudianteListOldMateriaEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriaEstudiante " + materiaEstudianteListOldMateriaEstudiante + " since its estudiante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<MateriaEstudiante> attachedMateriaEstudianteListNew = new ArrayList<MateriaEstudiante>();
            for (MateriaEstudiante materiaEstudianteListNewMateriaEstudianteToAttach : materiaEstudianteListNew) {
                materiaEstudianteListNewMateriaEstudianteToAttach = em.getReference(materiaEstudianteListNewMateriaEstudianteToAttach.getClass(), materiaEstudianteListNewMateriaEstudianteToAttach.getMateriaEstudiantePK());
                attachedMateriaEstudianteListNew.add(materiaEstudianteListNewMateriaEstudianteToAttach);
            }
            materiaEstudianteListNew = attachedMateriaEstudianteListNew;
            estudiante.setMateriaEstudianteList(materiaEstudianteListNew);
            estudiante = em.merge(estudiante);
            for (MateriaEstudiante materiaEstudianteListNewMateriaEstudiante : materiaEstudianteListNew) {
                if (!materiaEstudianteListOld.contains(materiaEstudianteListNewMateriaEstudiante)) {
                    Estudiante oldEstudianteOfMateriaEstudianteListNewMateriaEstudiante = materiaEstudianteListNewMateriaEstudiante.getEstudiante();
                    materiaEstudianteListNewMateriaEstudiante.setEstudiante(estudiante);
                    materiaEstudianteListNewMateriaEstudiante = em.merge(materiaEstudianteListNewMateriaEstudiante);
                    if (oldEstudianteOfMateriaEstudianteListNewMateriaEstudiante != null && !oldEstudianteOfMateriaEstudianteListNewMateriaEstudiante.equals(estudiante)) {
                        oldEstudianteOfMateriaEstudianteListNewMateriaEstudiante.getMateriaEstudianteList().remove(materiaEstudianteListNewMateriaEstudiante);
                        oldEstudianteOfMateriaEstudianteListNewMateriaEstudiante = em.merge(oldEstudianteOfMateriaEstudianteListNewMateriaEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estudiante.getCodigo();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
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
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MateriaEstudiante> materiaEstudianteListOrphanCheck = estudiante.getMateriaEstudianteList();
            for (MateriaEstudiante materiaEstudianteListOrphanCheckMateriaEstudiante : materiaEstudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the MateriaEstudiante " + materiaEstudianteListOrphanCheckMateriaEstudiante + " in its materiaEstudianteList field has a non-nullable estudiante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
