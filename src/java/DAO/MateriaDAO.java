/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Modelo.Materia;
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
public class MateriaDAO implements Serializable {
    
    public MateriaDAO() {
        this.emf=Persistence.createEntityManagerFactory("ExamenFinal_-_Sistema_de_NotasPU");
    }

    public MateriaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) throws PreexistingEntityException, Exception {
        if (materia.getMateriaEstudianteList() == null) {
            materia.setMateriaEstudianteList(new ArrayList<MateriaEstudiante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MateriaEstudiante> attachedMateriaEstudianteList = new ArrayList<MateriaEstudiante>();
            for (MateriaEstudiante materiaEstudianteListMateriaEstudianteToAttach : materia.getMateriaEstudianteList()) {
                materiaEstudianteListMateriaEstudianteToAttach = em.getReference(materiaEstudianteListMateriaEstudianteToAttach.getClass(), materiaEstudianteListMateriaEstudianteToAttach.getMateriaEstudiantePK());
                attachedMateriaEstudianteList.add(materiaEstudianteListMateriaEstudianteToAttach);
            }
            materia.setMateriaEstudianteList(attachedMateriaEstudianteList);
            em.persist(materia);
            for (MateriaEstudiante materiaEstudianteListMateriaEstudiante : materia.getMateriaEstudianteList()) {
                Materia oldMateriaOfMateriaEstudianteListMateriaEstudiante = materiaEstudianteListMateriaEstudiante.getMateria();
                materiaEstudianteListMateriaEstudiante.setMateria(materia);
                materiaEstudianteListMateriaEstudiante = em.merge(materiaEstudianteListMateriaEstudiante);
                if (oldMateriaOfMateriaEstudianteListMateriaEstudiante != null) {
                    oldMateriaOfMateriaEstudianteListMateriaEstudiante.getMateriaEstudianteList().remove(materiaEstudianteListMateriaEstudiante);
                    oldMateriaOfMateriaEstudianteListMateriaEstudiante = em.merge(oldMateriaOfMateriaEstudianteListMateriaEstudiante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMateria(materia.getCodigo()) != null) {
                throw new PreexistingEntityException("Materia " + materia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getCodigo());
            List<MateriaEstudiante> materiaEstudianteListOld = persistentMateria.getMateriaEstudianteList();
            List<MateriaEstudiante> materiaEstudianteListNew = materia.getMateriaEstudianteList();
            List<String> illegalOrphanMessages = null;
            for (MateriaEstudiante materiaEstudianteListOldMateriaEstudiante : materiaEstudianteListOld) {
                if (!materiaEstudianteListNew.contains(materiaEstudianteListOldMateriaEstudiante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriaEstudiante " + materiaEstudianteListOldMateriaEstudiante + " since its materia field is not nullable.");
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
            materia.setMateriaEstudianteList(materiaEstudianteListNew);
            materia = em.merge(materia);
            for (MateriaEstudiante materiaEstudianteListNewMateriaEstudiante : materiaEstudianteListNew) {
                if (!materiaEstudianteListOld.contains(materiaEstudianteListNewMateriaEstudiante)) {
                    Materia oldMateriaOfMateriaEstudianteListNewMateriaEstudiante = materiaEstudianteListNewMateriaEstudiante.getMateria();
                    materiaEstudianteListNewMateriaEstudiante.setMateria(materia);
                    materiaEstudianteListNewMateriaEstudiante = em.merge(materiaEstudianteListNewMateriaEstudiante);
                    if (oldMateriaOfMateriaEstudianteListNewMateriaEstudiante != null && !oldMateriaOfMateriaEstudianteListNewMateriaEstudiante.equals(materia)) {
                        oldMateriaOfMateriaEstudianteListNewMateriaEstudiante.getMateriaEstudianteList().remove(materiaEstudianteListNewMateriaEstudiante);
                        oldMateriaOfMateriaEstudianteListNewMateriaEstudiante = em.merge(oldMateriaOfMateriaEstudianteListNewMateriaEstudiante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = materia.getCodigo();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
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
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MateriaEstudiante> materiaEstudianteListOrphanCheck = materia.getMateriaEstudianteList();
            for (MateriaEstudiante materiaEstudianteListOrphanCheckMateriaEstudiante : materiaEstudianteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the MateriaEstudiante " + materiaEstudianteListOrphanCheckMateriaEstudiante + " in its materiaEstudianteList field has a non-nullable materia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
