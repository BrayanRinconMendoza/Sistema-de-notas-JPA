package Controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Modelo.Estudiante;
import Modelo.MateriaEstudiante;
import Negocio.SistemaNotas;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class Agregar_Estudiante_Existente_Materia extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        SistemaNotas sistema=new SistemaNotas();
        String codigoMateria=(String)request.getSession().getAttribute("codigoMateria");
        String[] datosEstudiante=request.getParameter("seleccion-estudiante").split("-");
        MateriaEstudiante me  =new MateriaEstudiante(sistema.buscarEstudiante(datosEstudiante[0]), sistema.buscarMateria(codigoMateria));
        if(sistema.insertarMateriaEstudiante(me)){
            request.getSession().setAttribute("codigoMateria", codigoMateria);
            request.getRequestDispatcher("./Vista/VistaMateria.jsp").forward(request, response);
        }else{
            request.getSession().setAttribute("codigoMateria", codigoMateria);
            request.getRequestDispatcher("./Vista/ErrorAgregarEstudiante.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}