<%-- 
    Document   : ErrorEliminarEstudiante
    Created on : 17-dic-2018, 23:48:05
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
    </head>
    <body>
        <%
            String codigoMateria=(String)request.getSession().getAttribute("codigoMateria");
            request.getSession().setAttribute("codigoMateria", codigoMateria);  
        %>
        
        <header class="container  header-no-border">
            <div class="col-sm-3"></div>
            <img alt="logo" id="logo" src="imagenes/logo_horizontal.jpg">
        </header>
         <nav class="navigation-bar">
            <ul>
                <li id="left-button-vista-materia">
                    <form action='volver_vista_materia.do' method='POST'>
                        <button type="submit"><i class='fas fa-arrow-left' style='font-size:24px'></i></button>
                    </form>
                </li>
            </ul>
        </nav>
        <h1 style="text-align: center;">Error al eliminar estudiante estudiante</h1>
    </body>
</html>
