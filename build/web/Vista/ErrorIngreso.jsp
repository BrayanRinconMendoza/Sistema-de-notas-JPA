<%-- 
    Document   : Error
    Created on : 11-dic-2018, 16:30:58
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Formulario de Ingreso</title>
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
        <script type="text/JavaScript" src="../js/funcionalidad.js"></script>
        <!--my css-->
        <link href="css/estilo.css" rel="stylesheet" type="text/css">
        <!--google icons-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    </head>
    <body>
        <header class="container">
            <div class="col-sm-3"></div>
            <img alt="logo" id="logo" src="imagenes/logo_horizontal.jpg">
        </header>
        <br>
        <div class="container" id="div-inicial">
            
            
            <form class="container" id="formulario-ingreso" action="leer_formulario_ingreso.do" method="POST">
                <br>
                <div class="alert alert-danger alert-dismissible">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong>Error!</strong> Credenciales Inconrrectas.
                </div>
                
                <label>Ingresar como</label>
                <select name="quien-ingresa">
                    <option>Estudiante</option>
                    <option>Docente</option>
                </select>
                <br>
                <br>
                <label>Ingrese sus credenciales de acceso</label><br>
                <input type="text" name="codigo" placeholder="Ingrese su Codigo" value="000" required/><br>
                <input type="password" name="password" placeholder="Ingrese su Contraseña" value="ufps" required/><br>

                <div  class="botones" >
                    <button type="submit">Ingresar</button>
                </div>

            </form>
        </div>
        <br>
        
        <footer class="footer">
            <p>Nombre: Jesus Romario Jaimes, Codigo: 1151104</p>
        </footer>
    </body>
</html>
