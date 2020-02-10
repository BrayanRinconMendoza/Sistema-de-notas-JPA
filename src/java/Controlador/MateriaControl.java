/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Controlador;

import Modelo.Materia;
import Modelo.MateriaEstudiantePK;
import Negocio.SistemaNotas;
import java.util.List;

/**
 *
 * @author Romario
 */
public class MateriaControl {
    
    SistemaNotas sistema=new SistemaNotas();
    
    public List<Materia> getMaterias(){
        return sistema.getMaterias();
    }
    
    public Materia buscarMateria(String codigo){
        return sistema.buscarMateria(codigo);
    }
    

}
