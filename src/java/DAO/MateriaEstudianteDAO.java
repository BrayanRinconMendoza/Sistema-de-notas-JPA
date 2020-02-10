/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Estudiante;
import Modelo.Materia;
import Modelo.MateriaEstudiante;
import Modelo.MateriaEstudiantePK;
import Modelo.Nota;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class MateriaEstudianteDAO implements Serializable {
    
    public MateriaEstudianteDAO() {
        this.emf=Persistence.createEntityManagerFactory("ExamenFinal_-_Sistema_de_NotasPU");
    }

    public MateriaEstudianteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MateriaEstudiante materiaEstudiante) throws PreexistingEntityException, Exception {
        System.err.println(materiaEstudiante.getEstudiante().getCodigo());
        System.err.println(materiaEstudiante.getMateria().getCodigo());
        if (materiaEstudiante.getMateriaEstudiantePK() == null) {
            materiaEstudiante.setMateriaEstudiantePK(new MateriaEstudiantePK());
        }
        if (materiaEstudiante.getNotaList() == null) {
            materiaEstudiante.setNotaList(new ArrayList<Nota>());
        }
        materiaEstudiante.getMateriaEstudiantePK().setCodMateria(materiaEstudiante.getMateria().getCodigo());
        materiaEstudiante.getMateriaEstudiantePK().setCodEstudiante(materiaEstudiante.getEstudiante().getCodigo());
        EntityManager em = null;
        

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = materiaEstudiante.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getCodigo());
                materiaEstudiante.setEstudiante(estudiante);
            }
            Materia materia = materiaEstudiante.getMateria();
            if (materia != null) {
                materia = em.getReference(materia.getClass(), materia.getCodigo());
                materiaEstudiante.setMateria(materia);
            }
            List<Nota> attachedNotaList = new ArrayList<Nota>();
            for (Nota notaListNotaToAttach : materiaEstudiante.getNotaList()) {
                notaListNotaToAttach = em.getReference(notaListNotaToAttach.getClass(), notaListNotaToAttach.getNotaPK());
                attachedNotaList.add(notaListNotaToAttach);
            }
            materiaEstudiante.setNotaList(attachedNotaList);
            em.persist(materiaEstudiante);
            if (estudiante != null) {
                estudiante.getMateriaEstudianteList().add(materiaEstudiante);
                estudiante = em.merge(estudiante);
            }
            if (materia != null) {
                materia.getMateriaEstudianteList().add(materiaEstudiante);
                materia = em.merge(materia);
            }
            for (Nota notaListNota : materiaEstudiante.getNotaList()) {
                MateriaEstudiante oldMateriaEstudianteOfNotaListNota = notaListNota.getMateriaEstudiante();
                notaListNota.setMateriaEstudiante(materiaEstudiante);
                notaListNota = em.merge(notaListNota);
                if (oldMateriaEstudianteOfNotaListNota != null) {
                    oldMateriaEstudianteOfNotaListNota.getNotaList().remove(notaListNota);
                    oldMateriaEstudianteOfNotaListNota = em.merge(oldMateriaEstudianteOfNotaListNota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMateriaEstudiante(materiaEstudiante.getMateriaEstudiantePK()) != null) {
                throw new PreexistingEntityException("MateriaEstudiante " + materiaEstudiante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MateriaEstudiante materiaEstudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        materiaEstudiante.getMateriaEstudiantePK().setCodMateria(materiaEstudiante.getMateria().getCodigo());
        materiaEstudiante.getMateriaEstudiantePK().setCodEstudiante(materiaEstudiante.getEstudiante().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriaEstudiante persistentMateriaEstudiante = em.find(MateriaEstudiante.class, materiaEstudiante.getMateriaEstudiantePK());
            Estudiante estudianteOld = persistentMateriaEstudiante.getEstudiante();
            Estudiante estudianteNew = materiaEstudiante.getEstudiante();
            Materia materiaOld = persistentMateriaEstudiante.getMateria();
            Materia materiaNew = materiaEstudiante.getMateria();
            List<Nota> notaListOld = persistentMateriaEstudiante.getNotaList();
            List<Nota> notaListNew = materiaEstudiante.getNotaList();
            List<String> illegalOrphanMessages = null;
            for (Nota notaListOldNota : notaListOld) {
                if (!notaListNew.contains(notaListOldNota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nota " + notaListOldNota + " since its materiaEstudiante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getCodigo());
                materiaEstudiante.setEstudiante(estudianteNew);
            }
            if (materiaNew != null) {
                materiaNew = em.getReference(materiaNew.getClass(), materiaNew.getCodigo());
                materiaEstudiante.setMateria(materiaNew);
            }
            List<Nota> attachedNotaListNew = new ArrayList<Nota>();
            for (Nota notaListNewNotaToAttach : notaListNew) {
                notaListNewNotaToAttach = em.getReference(notaListNewNotaToAttach.getClass(), notaListNewNotaToAttach.getNotaPK());
                attachedNotaListNew.add(notaListNewNotaToAttach);
            }
            notaListNew = attachedNotaListNew;
            materiaEstudiante.setNotaList(notaListNew);
            materiaEstudiante = em.merge(materiaEstudiante);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getMateriaEstudianteList().remove(materiaEstudiante);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getMateriaEstudianteList().add(materiaEstudiante);
                estudianteNew = em.merge(estudianteNew);
            }
            if (materiaOld != null && !materiaOld.equals(materiaNew)) {
                materiaOld.getMateriaEstudianteList().remove(materiaEstudiante);
                materiaOld = em.merge(materiaOld);
            }
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                materiaNew.getMateriaEstudianteList().add(materiaEstudiante);
                materiaNew = em.merge(materiaNew);
            }
            for (Nota notaListNewNota : notaListNew) {
                if (!notaListOld.contains(notaListNewNota)) {
                    MateriaEstudiante oldMateriaEstudianteOfNotaListNewNota = notaListNewNota.getMateriaEstudiante();
                    notaListNewNota.setMateriaEstudiante(materiaEstudiante);
                    notaListNewNota = em.merge(notaListNewNota);
                    if (oldMateriaEstudianteOfNotaListNewNota != null && !oldMateriaEstudianteOfNotaListNewNota.equals(materiaEstudiante)) {
                        oldMateriaEstudianteOfNotaListNewNota.getNotaList().remove(notaListNewNota);
                        oldMateriaEstudianteOfNotaListNewNota = em.merge(oldMateriaEstudianteOfNotaListNewNota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MateriaEstudiantePK id = materiaEstudiante.getMateriaEstudiantePK();
                if (findMateriaEstudiante(id) == null) {
                    throw new NonexistentEntityException("The materiaEstudiante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MateriaEstudiantePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriaEstudiante materiaEstudiante;
            try {
                materiaEstudiante = em.getReference(MateriaEstudiante.class, id);
                materiaEstudiante.getMateriaEstudiantePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materiaEstudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nota> notaListOrphanCheck = materiaEstudiante.getNotaList();
            for (Nota notaListOrphanCheckNota : notaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MateriaEstudiante (" + materiaEstudiante + ") cannot be destroyed since the Nota " + notaListOrphanCheckNota + " in its notaList field has a non-nullable materiaEstudiante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estudiante estudiante = materiaEstudiante.getEstudiante();
            if (estudiante != null) {
                estudiante.getMateriaEstudianteList().remove(materiaEstudiante);
                estudiante = em.merge(estudiante);
            }
            Materia materia = materiaEstudiante.getMateria();
            if (materia != null) {
                materia.getMateriaEstudianteList().remove(materiaEstudiante);
                materia = em.merge(materia);
            }
            em.remove(materiaEstudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MateriaEstudiante> findMateriaEstudianteEntities() {
        return findMateriaEstudianteEntities(true, -1, -1);
    }

    public List<MateriaEstudiante> findMateriaEstudianteEntities(int maxResults, int firstResult) {
        return findMateriaEstudianteEntities(false, maxResults, firstResult);
    }

    private List<MateriaEstudiante> findMateriaEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MateriaEstudiante.class));
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

    public MateriaEstudiante findMateriaEstudiante(MateriaEstudiantePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MateriaEstudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MateriaEstudiante> rt = cq.from(MateriaEstudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
