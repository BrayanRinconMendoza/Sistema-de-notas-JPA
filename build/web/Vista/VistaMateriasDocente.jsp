<%-- 
    Document   : VistaInicialDocente
    Created on : 11-dic-2018, 16:31:54
    Author     : Romario
--%>

<%@page import="java.util.List"%>
<%@page import="Modelo.Materia"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Controlador.MateriaControl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sistema de Notas</title>
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
        
        <header class="container  header-no-border">
            <div class="col-sm-3"></div>
            <img alt="logo" id="logo" src="imagenes/logo_horizontal.jpg">
        </header>
        
        <nav class="navigation-bar">
            <ul>
                <li>
                    <form action='cerrar_sesion.do' method='POST'>
                        <li><button id="salir" type="submit" ><i class='fas fa-power-off' style='font-size:24px'></i></button></li>
                    </form>
                </li>
            </ul>
        </nav>
        
        <br>
        <%
             MateriaControl materiaControl=new MateriaControl();
             String contenido="";
             List<Materia>materias = materiaControl.getMaterias();
             if(materias.isEmpty()){
                 contenido="<h4>No existen materias registradas</h4>";
             }else{
                contenido+="<table class='table table-bordered container' id='tabla-materias'>"
                        + "<thead>"
                        + "<tr>"
                        + "<th>Materia</th>"
                        + "<th>Codigo</th>"
                        + "<th># Estudiantes</th>"
                        + "<th>Ver</th>"
                        + "<th>Eliminar</th>"
                        + "</tr>"
                        + "</thead>"
                        + "<tbody>";
                for(Materia m:materias){
                     contenido+="<tr>"
                            + "<td>"+m.getNombre()+"</td>"
                            + "<td>"+m.getCodigo()+"</td>"
                            + "<td>"+m.getMateriaEstudianteList().size()+"</td>"
                            + "<td>"
                                + "<form action='visualizar_materia.do' method='POST'>"
                                + "<input type='text' name='materia-a-ver' style='display:none;' value='"+m.getCodigo()+"' required/>"
                                + "<button type='submit'><i class='material-icons'>remove_red_eye</i></button>"
                                + "</form>"
                            + "</td>"
                            + "<td><button type='button' onclick='generarMensajeEliminarMateria("+m.getCodigo()+")'><i class='material-icons'>delete_forever</i></button></td>"
                            + "</tr>";
                }
                contenido+="</tbody></table>";
            }
        %>
        
        <div>
            <%=contenido%>
        </div>
        
        <div id="ovelay-mensaje-eliminar-materia"></div>
        
        <div id="imp-formulario-creacion-materia"></div><br>
        
        <div  class="botones" >
            <button type="button" onclick='generarFormCreacionMateria()'>Registrar Materia</button>
        </div>
    </body>
</html>
