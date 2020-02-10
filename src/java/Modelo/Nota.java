/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n")
    , @NamedQuery(name = "Nota.findByNota", query = "SELECT n FROM Nota n WHERE n.nota = :nota")
    , @NamedQuery(name = "Nota.findByCodEstudiante", query = "SELECT n FROM Nota n WHERE n.notaPK.codEstudiante = :codEstudiante")
    , @NamedQuery(name = "Nota.findById", query = "SELECT n FROM Nota n WHERE n.notaPK.id = :id")
    , @NamedQuery(name = "Nota.findByCodMateria", query = "SELECT n FROM Nota n WHERE n.notaPK.codMateria = :codMateria")})
public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotaPK notaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nota")
    private Double nota;
    @JoinColumns({
        @JoinColumn(name = "cod_estudiante", referencedColumnName = "cod_estudiante", insertable = false, updatable = false)
        , @JoinColumn(name = "cod_materia", referencedColumnName = "cod_materia", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private MateriaEstudiante materiaEstudiante;

    public Nota() {
    }

    public Nota(NotaPK notaPK) {
        this.notaPK = notaPK;
    }

    public Nota(Double nota, MateriaEstudiante materiaEstudiante, int id) {
        this.nota = nota;
        this.materiaEstudiante = materiaEstudiante;
        this.notaPK=new NotaPK(materiaEstudiante.getEstudiante().getCodigo(), id, materiaEstudiante.getMateria().getCodigo());
    }

    public Nota(String codEstudiante, int id, String codMateria) {
        this.notaPK = new NotaPK(codEstudiante, id, codMateria);
    }

    public NotaPK getNotaPK() {
        return notaPK;
    }

    public void setNotaPK(NotaPK notaPK) {
        this.notaPK = notaPK;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public MateriaEstudiante getMateriaEstudiante() {
        return materiaEstudiante;
    }

    public void setMateriaEstudiante(MateriaEstudiante materiaEstudiante) {
        this.materiaEstudiante = materiaEstudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notaPK != null ? notaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.notaPK == null && other.notaPK != null) || (this.notaPK != null && !this.notaPK.equals(other.notaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Nota[ notaPK=" + notaPK + " ]";
    }
    
}
