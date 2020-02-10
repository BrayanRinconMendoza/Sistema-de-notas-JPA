/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.MateriaEstudiante;
import Modelo.Nota;
import Modelo.NotaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class NotaDAO implements Serializable {
    
    public NotaDAO() {
        this.emf=Persistence.createEntityManagerFactory("ExamenFinal_-_Sistema_de_NotasPU");
    }

    public NotaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nota nota) throws PreexistingEntityException, Exception {
        if (nota.getNotaPK() == null) {
            nota.setNotaPK(new NotaPK());
        }
        nota.getNotaPK().setCodEstudiante(nota.getMateriaEstudiante().getMateriaEstudiantePK().getCodEstudiante());
        nota.getNotaPK().setCodMateria(nota.getMateriaEstudiante().getMateriaEstudiantePK().getCodMateria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriaEstudiante materiaEstudiante = nota.getMateriaEstudiante();
            if (materiaEstudiante != null) {
                materiaEstudiante = em.getReference(materiaEstudiante.getClass(), materiaEstudiante.getMateriaEstudiantePK());
                nota.setMateriaEstudiante(materiaEstudiante);
            }
            em.persist(nota);
            if (materiaEstudiante != null) {
                materiaEstudiante.getNotaList().add(nota);
                materiaEstudiante = em.merge(materiaEstudiante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNota(nota.getNotaPK()) != null) {
                throw new PreexistingEntityException("Nota " + nota + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nota nota) throws NonexistentEntityException, Exception {
        nota.getNotaPK().setCodEstudiante(nota.getMateriaEstudiante().getMateriaEstudiantePK().getCodEstudiante());
        nota.getNotaPK().setCodMateria(nota.getMateriaEstudiante().getMateriaEstudiantePK().getCodMateria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nota persistentNota = em.find(Nota.class, nota.getNotaPK());
            MateriaEstudiante materiaEstudianteOld = persistentNota.getMateriaEstudiante();
            MateriaEstudiante materiaEstudianteNew = nota.getMateriaEstudiante();
            if (materiaEstudianteNew != null) {
                materiaEstudianteNew = em.getReference(materiaEstudianteNew.getClass(), materiaEstudianteNew.getMateriaEstudiantePK());
                nota.setMateriaEstudiante(materiaEstudianteNew);
            }
            nota = em.merge(nota);
            if (materiaEstudianteOld != null && !materiaEstudianteOld.equals(materiaEstudianteNew)) {
                materiaEstudianteOld.getNotaList().remove(nota);
                materiaEstudianteOld = em.merge(materiaEstudianteOld);
            }
            if (materiaEstudianteNew != null && !materiaEstudianteNew.equals(materiaEstudianteOld)) {
                materiaEstudianteNew.getNotaList().add(nota);
                materiaEstudianteNew = em.merge(materiaEstudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NotaPK id = nota.getNotaPK();
                if (findNota(id) == null) {
                    throw new NonexistentEntityException("The nota with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NotaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nota nota;
            try {
                nota = em.getReference(Nota.class, id);
                nota.getNotaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nota with id " + id + " no longer exists.", enfe);
            }
            MateriaEstudiante materiaEstudiante = nota.getMateriaEstudiante();
            if (materiaEstudiante != null) {
                materiaEstudiante.getNotaList().remove(nota);
                materiaEstudiante = em.merge(materiaEstudiante);
            }
            em.remove(nota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nota> findNotaEntities() {
        return findNotaEntities(true, -1, -1);
    }

    public List<Nota> findNotaEntities(int maxResults, int firstResult) {
        return findNotaEntities(false, maxResults, firstResult);
    }

    private List<Nota> findNotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nota.class));
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

    public Nota findNota(NotaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nota.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nota> rt = cq.from(Nota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
