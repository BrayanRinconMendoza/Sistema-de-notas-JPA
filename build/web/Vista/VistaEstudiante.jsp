<%-- 
    Document   : VistaEstudiante
    Created on : 11-dic-2018, 17:05:48
    Author     : Romario
--%>

<%@page import="Controlador.MateriaEstudianteControl"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Nota"%>
<%@page import="Modelo.MateriaEstudiante"%>
<%@page import="Modelo.Estudiante"%>
<%@page import="Controlador.EstudianteControl"%>
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
            String codigoEstudiante=(String)request.getSession().getAttribute("codigo");
            EstudianteControl estudianteControl=new EstudianteControl();
            Estudiante estudiante=estudianteControl.buscarEstudiante(codigoEstudiante);
            MateriaEstudianteControl meControl=new MateriaEstudianteControl();
            String contenido="";
            if(estudiante.getMateriaEstudianteList().isEmpty()){
                contenido="<h4>Bienvenido "+estudiante.getNombre()+"</h4><br><h4>No estas registrado en ninguna materia</h4>";
            }else{
                for(MateriaEstudiante me:estudiante.getMateriaEstudianteList()){
                    List<Nota> notas=me.getNotaList();
                    String notasMateria="";
                    contenido+="<h4>Bienvenido "+estudiante.getNombre()+"</h4><br>";
                    contenido+="<h5>"+me.getMateria().getNombre()+", "+me.getMateria().getCodigo()+"</h5>";
                    contenido+="<table class='table table-bordered container'>"
                        + "<thead>";
                    for(int i=0;i<notas.size();i++){
                        contenido+="<th>Nota "+(i+1)+"</th>";
                        notasMateria+="<td>"+notas.get(i).getNota()+"</td>";
                    }
                    contenido+="<th>Promedio</th>"
                            + "</thead>"
                            + "<tbody>"
                            + notasMateria
                            + "<td>"+meControl.getPromedio(estudiante.getCodigo(), me.getMateria().getCodigo())
                            + "</tbody></table>";
                }
            }
            
        %>
        <div class="container">
            <%=contenido%>
        </div>
       
        
</html>
