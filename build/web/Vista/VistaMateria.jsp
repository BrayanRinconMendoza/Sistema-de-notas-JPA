<%-- 
    Document   : VistaMatera
    Created on : 12-dic-2018, 12:14:05
    Author     : Romario
--%>

<%@page import="Controlador.MateriaEstudianteControl"%>
<%@page import="Modelo.Materia"%>
<%@page import="Controlador.MateriaControl"%>
<%@page import="Modelo.Nota"%>
<%@page import="Modelo.MateriaEstudiante"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Estudiante"%>
<%@page import="Controlador.EstudianteControl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--bootstrap-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <!--ajax api-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <!--my javascirpt-->
        <script type="text/JavaScript" src="js/funcionalidad.js"></script>
        <!--my css-->
        <link href="css/estilo.css" rel="stylesheet" type="text/css">
        <!--google icons-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <!--awesome icons-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.5.0/css/all.css' integrity='sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU' crossorigin='anonymous'>
    </head>
    
    <body>
        <%
            String codigoMateria=(String)request.getSession().getAttribute("codigoMateria");
            request.getSession().setAttribute("codigoMateria", codigoMateria);
            MateriaControl materiaControl=new MateriaControl();
            EstudianteControl estudianteControl=new EstudianteControl();
            MateriaEstudianteControl meControl=new MateriaEstudianteControl();
            Materia materia=materiaControl.buscarMateria(codigoMateria);
            String tablaEstudiantes="";
            String display="";
            if(materia.getMateriaEstudianteList().isEmpty()){
                tablaEstudiantes="<h4>No hay estudiantes registrados en esta materia</h4>";
            }else{
                List<Estudiante> estudiantes=meControl.getEstudiantesMateria(materia);
                List<List<Double>> notasEstudiantes=meControl.getNotasEstudiantesMateria(materia);
                int cantidadEstudiantes=estudiantes.size();
                String headNotas="";
                int cantidadNotasEstudiante=notasEstudiantes.get(0).size();
                if(cantidadNotasEstudiante>0){
                    display="style='display: none;'";
                }
                for(int i=0;i<cantidadNotasEstudiante;i++){
                    headNotas+="<th>Nota"+(i+1)+"</th>";
                }
                headNotas+="<th>Nota<button id='boton-ingresar-notas' type='button' onclick='crearColumnaIngresoNotas("+cantidadEstudiantes+","+cantidadNotasEstudiante+")'><i class='fa fa-plus'></i></button></th>";
            
                tablaEstudiantes+="<form action='guardar_notas.do'><table name='tabla-estudiantes' class='table table-bordered container' id='tabla-estudiantes'>"
                        + "<thead>"
                        + "<tr  id='head-row'>"
                        + "<th>Codigo</th>"
                        + "<th>Nombre</th>"
                        + "<th>Email</th>"
                        + headNotas
                        + "<th>Promedio</th>"
                        + "<th>Eliminar</th>"
                        + "</tr>"
                        + "</thead>"
                        + "<tbody>";
                for(int i=0;i<estudiantes.size();i++){
                    tablaEstudiantes+="<tr id='table-row-"+i+"'>"
                            + "<td>"+estudiantes.get(i).getCodigo()+"</td>"
                            + "<td>"+estudiantes.get(i).getNombre()+"</td>"
                            + "<td>"+estudiantes.get(i).getEmail()+"</td>";
                    for(int j=0;j<notasEstudiantes.get(i).size();j++){
                        tablaEstudiantes+="<td>"+notasEstudiantes.get(i).get(j)+"</td>";
                    }
                     tablaEstudiantes+="</form><td class='cell-nota'></td>"
                            + "<td>"+meControl.getPromedio(estudiantes.get(i).getCodigo(), codigoMateria)+"</td>"
                            + "<td><button class='boton-eliminar-estudiante' type='button' onclick='generarMensajeEliminarEstudiante("+estudiantes.get(i).getCodigo()+")'><i class='material-icons'>delete_forever</i></button></td>"
                            + "</tr>";
                }
                tablaEstudiantes+="</tbody></table>"; 
            }
        %>
        
        <header class="container header-no-border">
            <div class="col-sm-3"></div>
            <img alt="logo" id="logo" src="imagenes/logo_horizontal.jpg">
        </header>
        
        <nav class="navigation-bar">
            <ul>
                <li id="left-button-vista-materia">
                    <form action='volver.do' method='POST'>
                        <button type="submit"><i class='fas fa-arrow-left' style='font-size:24px'></i></button>
                    </form>
                </li>
            </ul>
        </nav>
        <br>
        
        <h1 id="titulo-pagina"><%=materia.getNombre()%>, <%=codigoMateria%></h1>
        
        <div id="tabla-estudiantes">
            <%=tablaEstudiantes%>
        </div>
        
        <!--<div>
            <form action="agregar_estudiantes_txt.do">
                <div class="botones">
                    <input type="file" id="files" name="archivo-txt"/>
                </div>
            </form>
        </div>-->
        
        
        <%
            String opciones="";
            for(Estudiante e:estudianteControl.getEstudiantes()){
                opciones+="<option>"+e.getCodigo()+"-"+e.getNombre()+"</option>";
            }
        %>
        
        <form  <%=display%>action='agregar_estudiante_existente_materia.do' id="formulario-agregar-estudiante" method='POST'>
            <select id="selec-estudiantes"name="seleccion-estudiante">
                <%=opciones%>
            </select>
            <button type="submit" onclick="">Agregar Estudiante Existente</button>
        </form>
        
        <div id="imp-formulario-creacion-estudiante"></div>
        
        <div class="botones" <%=display%>>
            <button type="button" onclick='generarFormCreacionEstudiante()'>Registrar Nuevo Estudiante</button>
        </div>
        
        <div id="ovelay-mensaje-eliminar-estudiante"></div>
        
        <div id="ovelay-mensaje-primer-registro-notas"></div>
        
        
    </body>
</html>
