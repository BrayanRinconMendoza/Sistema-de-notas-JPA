/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.EstudianteDAO;
import DAO.MateriaDAO;
import DAO.MateriaEstudianteDAO;
import DAO.NotaDAO;
import Modelo.Estudiante;
import Modelo.Materia;
import Modelo.MateriaEstudiante;
import Modelo.MateriaEstudiantePK;
import Modelo.Nota;
import Modelo.NotaPK;
import Util.ServicioEmail;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Romario
 */
public class SistemaNotas {
    EstudianteDAO estudianteDAO=new EstudianteDAO();
    MateriaDAO materiaDAO=new MateriaDAO();
    MateriaEstudianteDAO materiaEstudianteDAO=new MateriaEstudianteDAO();
    NotaDAO notaDAO=new NotaDAO();
    
    public List<Estudiante> getEstudiantes(){
        List<Estudiante> estudiantes;
        estudiantes=estudianteDAO.findEstudianteEntities();
        return(estudiantes);
    }
    
    public boolean insertarEstudiante(Estudiante nuevoEstudiante){
        try{
            this.estudianteDAO.create(nuevoEstudiante);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
     public boolean insertarMateria(Materia nuevaMateria){
        try{
            this.materiaDAO.create(nuevaMateria);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public List<Materia> getMaterias() {
        List<Materia> materias;
        materias=materiaDAO.findMateriaEntities();
        return(materias);
    }
     
    public boolean verificarPasswordDocente(String codigo, String password){
        if(codigo.equals("000") && password.equals("ufps")){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificarPasswordEstudiante(String codigo, String password) {
        Estudiante est=this.buscarEstudiante(codigo);
        if(est.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }

    public Materia buscarMateria(String codigo) {
        return materiaDAO.findMateria(codigo);
    }

    public List<MateriaEstudiante> getMateriasEstudiantes() {
        List<MateriaEstudiante> materiasEstudiantes;
        materiasEstudiantes=materiaEstudianteDAO.findMateriaEstudianteEntities();
        return(materiasEstudiantes);
    }

    public List<Nota> getNotas() {
        List<Nota> notas;
        notas=notaDAO.findNotaEntities();
        return notas;
    }
    
    public List<Nota> getMateriaEstudianteNotas(MateriaEstudiantePK id) {
        List<Nota> notas;
        notas=materiaEstudianteDAO.findMateriaEstudiante(id).getNotaList();
        return notas;
    }
    
    public String generarPassword(String codigo){
        String password = codigo+"_";
        Random rnd = new Random();

        for (int i = 0; i < 5; i++){
            password += rnd.nextInt(10);
        }
        return password;
    }
    
    public void enviarEmailPassword(String password, String email){
        String emailUsuarioEmisor="ejemplo.email.ufps@gmail.com";
        String clave="nfrbdxklxggkgoko";
        ServicioEmail servicioEmail=new ServicioEmail(emailUsuarioEmisor, clave);
        String emailReceptor=email;
        servicioEmail.enviarEmail(emailReceptor, "", "Su contraseña para ingresas al sitemas de notas es: "+password);
    }

    public boolean actualizarMateria(Materia materia,  MateriaEstudiante me) {
        try{
            Materia materiaExistente=buscarMateria(materia.getCodigo());
            materiaExistente.setNombre(materia.getNombre());
            List<MateriaEstudiante>meList=materia.getMateriaEstudianteList();
            meList.add(me);
            materiaExistente.setMateriaEstudianteList(meList);
            this.materiaDAO.edit(materiaExistente);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertarMateriaEstudiante(MateriaEstudiante materiaEstudiante) {
        try{
            this.materiaEstudianteDAO.create(materiaEstudiante);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public Estudiante buscarEstudiante(String codigo) {
        return estudianteDAO.findEstudiante(codigo);
    }

    public boolean actualizarEstudiante(Estudiante estudiante, MateriaEstudiante me) {
        try{
            Estudiante estudianteExistente=this.buscarEstudiante(estudiante.getCodigo());
            List<MateriaEstudiante>meList=estudiante.getMateriaEstudianteList();
            meList.add(me);
            estudianteExistente.setMateriaEstudianteList(meList);
            estudianteDAO.edit(estudianteExistente);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public List<Estudiante> getEstudiantesMateria(Materia materia){
        List<Estudiante> estudiante=new ArrayList<>();
        for(MateriaEstudiante me:materia.getMateriaEstudianteList()){
            estudiante.add(me.getEstudiante());
        }
        return estudiante;
    }

    public boolean eliminarMateria(Materia materia){
        try{
            for(MateriaEstudiante me:materia.getMateriaEstudianteList()){
                eliminarEstudianteDeMateria(me.getEstudiante().getCodigo(), materia.getCodigo());
            }
            materiaDAO.destroy(materia.getCodigo());
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean eliminarEstudianteDeMateria(String codigoEstudiante, String codigoMateria) {
        
        try{
            MateriaEstudiantePK mePK=new MateriaEstudiantePK(codigoEstudiante, codigoMateria);
            MateriaEstudiante me= materiaEstudianteDAO.findMateriaEstudiante(mePK);
            for(Nota n:me.getNotaList()){
                notaDAO.destroy(n.getNotaPK());
            }
            materiaEstudianteDAO.destroy(mePK);
            
            
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public List<Double> sortNotas(List<Nota> notas){
        List<Double> notasSorted=new ArrayList<>();
        for(Nota n:notas){
            notasSorted.add(n.getNota());
        }
        Collections.sort(notasSorted);
        return notasSorted;
    }
        
    public double calcularPromedio(MateriaEstudiantePK mepk){
        MateriaEstudiante me =materiaEstudianteDAO.findMateriaEstudiante(mepk);
        double promedio=0;
        List<Nota> notas= me.getNotaList();
        if(!notas.isEmpty()){
            if(notas.size()==1){
                promedio=notas.get(0).getNota();
            }else{
                List<Double> notasDouble=sortNotas(notas);
                for(int i=1;i<notasDouble.size();i++){
                    promedio+=notasDouble.get(i);
                }
                promedio=promedio/(notasDouble.size()-1);
            }
        }
        return (double)Math.round(promedio * 100d) / 100d;
    }
    
    public void guardarNotas(String[] notas, Materia materia){
        List<Double> notasDouble=new ArrayList<>();
        for(int i=0;i<notas.length;i++){
            notasDouble.add(Double.parseDouble(notas[i]));
        }
        List<MateriaEstudiante> materiaEstudiantes= materia.getMateriaEstudianteList();
        for(int i=0;i<materiaEstudiantes.size();i++){
            Nota nota=new Nota(notasDouble.get(i), materiaEstudiantes.get(i), this.getNotas().size());
            this.insertarNota(nota);
        }
    }
    
    public boolean insertarNota(Nota nota){
        try{
            notaDAO.create(nota);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public List<List<Double>> getNotasEstudiantesMateria(Materia materia){
        
        List<List<Double>> notasEstudiante=new ArrayList<>();
        for(MateriaEstudiante me:materia.getMateriaEstudianteList()){
            List<Double> notas=new ArrayList<>();
            for(Nota n:me.getNotaList()){
                notas.add(n.getNota());
            }
            notasEstudiante.add(notas);
        }
        return notasEstudiante;
    }
    
}
