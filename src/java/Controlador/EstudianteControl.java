/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.MateriaEstudiantePK;
import Negocio.SistemaNotas;
import java.util.List;

/**
 *
 * @author Romario
 */
public class EstudianteControl {
    
    SistemaNotas sistema = new SistemaNotas();
    
    public List<Estudiante> getEstudiantes(){
        return sistema.getEstudiantes();
    }

    public Estudiante buscarEstudiante(String codigoEstudiante) {
        return sistema.buscarEstudiante(codigoEstudiante);
    }
    
}
