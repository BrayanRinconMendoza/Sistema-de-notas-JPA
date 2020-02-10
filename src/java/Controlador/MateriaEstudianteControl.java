/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.Materia;
import Modelo.MateriaEstudiante;
import Modelo.MateriaEstudiantePK;
import Modelo.Nota;
import Negocio.SistemaNotas;
import java.util.List;

/**
 *
 * @author Romario
 */
public class MateriaEstudianteControl {
    
    SistemaNotas sistema=new SistemaNotas();
    
    public List<MateriaEstudiante> getMateriaEstudiantes(){
        return sistema.getMateriasEstudiantes();
    }
    
    public boolean crearMateriaEstudiantes(String codEstudiante, String codMateria){
        MateriaEstudiante materiaEstudiante=new MateriaEstudiante(codEstudiante, codMateria);
        return sistema.insertarMateriaEstudiante(materiaEstudiante);
    }
    
    public List<Nota> getMateriaEstudianteNotas(String codMateria, String codEstudiante) {
        MateriaEstudiantePK mePK=new MateriaEstudiantePK();
        return sistema.getMateriaEstudianteNotas(mePK);
    }

    public boolean crearMateriaEstudiantes(MateriaEstudiantePK mePK) {
        MateriaEstudiante materiaEstudiante=new MateriaEstudiante(mePK);
        return  sistema.insertarMateriaEstudiante(materiaEstudiante);
    }
    
     public List<Estudiante> getEstudiantesMateria(Materia materia){
         return sistema.getEstudiantesMateria(materia);
     }
    
    public double getPromedio(String codigo, String codigoMateria){
        MateriaEstudiantePK mePK=new MateriaEstudiantePK(codigo, codigoMateria);
        return sistema.calcularPromedio(mePK);
    }
    
    public List<List<Double>> getNotasEstudiantesMateria(Materia materia){
        return sistema.getNotasEstudiantesMateria(materia);
    }
}
