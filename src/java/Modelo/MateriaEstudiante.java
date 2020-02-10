/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "materia_estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MateriaEstudiante.findAll", query = "SELECT m FROM MateriaEstudiante m")
    , @NamedQuery(name = "MateriaEstudiante.findByCodEstudiante", query = "SELECT m FROM MateriaEstudiante m WHERE m.materiaEstudiantePK.codEstudiante = :codEstudiante")
    , @NamedQuery(name = "MateriaEstudiante.findByCodMateria", query = "SELECT m FROM MateriaEstudiante m WHERE m.materiaEstudiantePK.codMateria = :codMateria")})
public class MateriaEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MateriaEstudiantePK materiaEstudiantePK;
    @JoinColumn(name = "cod_estudiante", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estudiante estudiante;
    @JoinColumn(name = "cod_materia", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiaEstudiante")
    private List<Nota> notaList;

    public MateriaEstudiante() {
    }

    public MateriaEstudiante(MateriaEstudiantePK materiaEstudiantePK) {
        this.materiaEstudiantePK = materiaEstudiantePK;
    }

    public MateriaEstudiante(String codEstudiante, String codMateria) {
        this.materiaEstudiantePK = new MateriaEstudiantePK(codEstudiante, codMateria);
    }

    public MateriaEstudiante(Estudiante estudiante, Materia materia) {
        this.estudiante = estudiante;
        this.materia = materia;
    }
    
    

    public MateriaEstudiantePK getMateriaEstudiantePK() {
        return materiaEstudiantePK;
    }

    public void setMateriaEstudiantePK(MateriaEstudiantePK materiaEstudiantePK) {
        this.materiaEstudiantePK = materiaEstudiantePK;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materiaEstudiantePK != null ? materiaEstudiantePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaEstudiante)) {
            return false;
        }
        MateriaEstudiante other = (MateriaEstudiante) object;
        if ((this.materiaEstudiantePK == null && other.materiaEstudiantePK != null) || (this.materiaEstudiantePK != null && !this.materiaEstudiantePK.equals(other.materiaEstudiantePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.MateriaEstudiante[ materiaEstudiantePK=" + materiaEstudiantePK + " ]";
    }
    
}
