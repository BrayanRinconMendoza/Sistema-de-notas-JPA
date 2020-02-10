/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Romario
 */
@Embeddable
public class NotaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_estudiante")
    private String codEstudiante;
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "cod_materia")
    private String codMateria;

    public NotaPK() {
    }

    public NotaPK(String codEstudiante, int id, String codMateria) {
        this.codEstudiante = codEstudiante;
        this.id = id;
        this.codMateria = codMateria;
    }

    public String getCodEstudiante() {
        return codEstudiante;
    }

    public void setCodEstudiante(String codEstudiante) {
        this.codEstudiante = codEstudiante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(String codMateria) {
        this.codMateria = codMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstudiante != null ? codEstudiante.hashCode() : 0);
        hash += (int) id;
        hash += (codMateria != null ? codMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaPK)) {
            return false;
        }
        NotaPK other = (NotaPK) object;
        if ((this.codEstudiante == null && other.codEstudiante != null) || (this.codEstudiante != null && !this.codEstudiante.equals(other.codEstudiante))) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if ((this.codMateria == null && other.codMateria != null) || (this.codMateria != null && !this.codMateria.equals(other.codMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.NotaPK[ codEstudiante=" + codEstudiante + ", id=" + id + ", codMateria=" + codMateria + " ]";
    }
    
}
