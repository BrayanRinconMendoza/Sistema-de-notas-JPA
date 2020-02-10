/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e")
    , @NamedQuery(name = "Estudiante.findByCodigo", query = "SELECT e FROM Estudiante e WHERE e.codigo = :codigo")
    , @NamedQuery(name = "Estudiante.findByEmail", query = "SELECT e FROM Estudiante e WHERE e.email = :email")
    , @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Estudiante.findByPassword", query = "SELECT e FROM Estudiante e WHERE e.password = :password")
    , @NamedQuery(name = "Estudiante.findByTelefono", query = "SELECT e FROM Estudiante e WHERE e.telefono = :telefono")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "email")
    private String email;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "password")
    private String password;
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<MateriaEstudiante> materiaEstudianteList;

    public Estudiante() {
    }

    public Estudiante(String codigo, String nombre, String email, String telefono, String password) {
        this.codigo = codigo;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.telefono = telefono;
    }

    
    
    public Estudiante(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public List<MateriaEstudiante> getMateriaEstudianteList() {
        return materiaEstudianteList;
    }

    public void setMateriaEstudianteList(List<MateriaEstudiante> materiaEstudianteList) {
        this.materiaEstudianteList = materiaEstudianteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Estudiante[ codigo=" + codigo + " ]";
    }
    
}
