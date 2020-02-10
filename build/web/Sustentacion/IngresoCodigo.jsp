<%-- 
    Document   : IngresoCodigo
    Created on : 18-dic-2018, 8:32:24
    Author     : Romario
--%>

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
        <form class="container" id="formulario-ingreso" action="leer_form_no_matriculado.do" method="POST">
                <label>Ingresar codigo del estudiante</label>
                <input type="text" name="codigo" placeholder="Ingrese su Codigo" required/><br>

                <div  class="botones" >
                    <button type="submit">Ingresar</button>
                </div>
                
                

            </form>
    </body>
</html>
