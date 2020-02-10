
function generarFormCreacionMateria(){
    var form_div=document.getElementById("imp-formulario-creacion-materia");
    var form="<form class='container' id='formulario-crear-materia' action='crear_nueva_materia.do' method='POST'>";
            
    form+="<label><b>Ingrese los datos de la Materia<b></label><br>";
    form+="<input type='text' name='codigo-materia' placeholder='Codigo de la Materia' required/>";
    form+="<input type='text' name='nombre-materia' placeholder='Nombre de la Materia' required/><br>";
    form+="<br><button type='submit'>Registrar</button>";
    form+="<button type='button' onclick='cancelarCreacionMateria()'>Cancelar</button>";
    form+="</form>";
    form_div.innerHTML=form;
    document.getElementById("imp-formulario-creacion-materia").style.display = "block";
}


function cancelarCreacionMateria(){
    document.getElementById("imp-formulario-creacion-materia").style.display = "none";
}

function cancelarCreacionEstudiante(){
    document.getElementById("imp-formulario-creacion-estudiante").style.display = "none";
}

function generarFormCreacionEstudiante(){
    var form_div=document.getElementById("imp-formulario-creacion-estudiante");
    var form="<form class='container' id='formulario-crear-estudiante' action='crear_nuevo_estudiante.do' method='POST'>";
            
    form+="<label><b>Ingrese los datos del estudiante<b></label><br>";
    form+="<input type='text' name='codigo-estudiante' placeholder='Codigo del Estudiante' required/>";
    form+="<input type='text' name='nombre-estudiante' placeholder='Nombre del Estudiante' required/>";
    form+="<input type='email' name='email-estudiante' placeholder='Email del Estudiante' required/>";
    form+="<input type='text' name='telefono-estudiante' placeholder='Telefono del Estudiante' required/>";
    form+="<br><button type='submit'>Resgistrar</button>";
    form+="<button type='button' onclick='cancelarCreacionEstudiante()'>Cancelar</button>";
    form+="</form><br>";
    form_div.innerHTML=form;
    document.getElementById("imp-formulario-creacion-estudiante").style.display = "block";
}

function crearColumnaIngresoNotas(cantidadEstudiantes, cantidadNotas){
    if(cantidadNotas==0){
        var form_div=document.getElementById("ovelay-mensaje-primer-registro-notas");
        var form="<div id='mensaje-primer-registro-notas'><p><b>Una vez que se hayan registado notas a los estudinates no podra agregar mas estudiantes a esta materia<b><p>";
        form+="<div class='botones'>";    
        form+="<button type='button' onclick='mensajeRegistroNotasOff()'>Aceptar</button>";
        form+="</div>";
        form+="</div>";
        form_div.innerHTML=form;
        document.getElementById("ovelay-mensaje-primer-registro-notas").style.display = "block";
    }
    
    for(i=0;i<cantidadEstudiantes;i++){
        var row=document.getElementById("table-row-"+i);
        row.cells[2+cantidadNotas].innerHTML="<input class='input-nota' name='nota' type='number'step='any' placeholder='nota' required min='0' max='5' required/>";
    }
    var headRow=document.getElementById("head-row");
    headRow.cells[2+cantidadNotas].innerHTML="<button class='boton-guardar-notas' type='submit'><i class='fas fa-save' style='font-size:24px'></i></button><button type='button' onclick='cancelarColumnaIngresoNotas("+cantidadEstudiantes+","+cantidadNotas+")'><i class='fa fa-close' style='font-size:24px'></i></button>";
}

function cancelarColumnaIngresoNotas(cantidadEstudiantes, cantidadNotasEstudiante){
    for(i=0;i<cantidadEstudiantes;i++){
        var row=document.getElementById("table-row-"+i);
        row.cells[2+cantidadNotasEstudiante].innerHTML="";
    }
    var headRow=document.getElementById("head-row");
    headRow.cells[2+cantidadNotasEstudiante].innerHTML="Nota<button id='boton-ingresar-notas' type='button' onclick='crearColumnaIngresoNotas("+cantidadEstudiantes+","+cantidadNotasEstudiante+")'><i class='fa fa-plus'></i></button>";
}

function mensajeRegistroNotasOff() {
  document.getElementById("ovelay-mensaje-primer-registro-notas").style.display = "none";
}

function mensajeEliminarMateriaOverlayOff() {
  document.getElementById("ovelay-mensaje-eliminar-materia").style.display = "none";
}

function mensajeEliminarEstudianteOverlayOff() {
  document.getElementById("ovelay-mensaje-eliminar-estudiante").style.display = "none";
}

function generarMensajeEliminarMateria(codigo) {
    var form_div=document.getElementById("ovelay-mensaje-eliminar-materia");
    var form="<form id='mensaje-eliminar-materia' action='eliminar_materia.do' method='POST'>";
    
    form+="<input type='text' name='materia-a-eliminar' style='display:none;' value='"+codigo+"' required/>";
    form+="<p><b>Esta seguro de que desea eliminar la materia: "+codigo+"<b><p>";
    form+="<div class='botones'>";
    form+="<button type='submit'>Eliminar</button>";     
    form+="<button type='button' onclick='mensajeEliminarMateriaOverlayOff()'>Cancelar</button>";
    form+="</div>";
    form+="</form>";
    form_div.innerHTML=form;
    document.getElementById("ovelay-mensaje-eliminar-materia").style.display = "block";
}

function generarMensajeEliminarEstudiante(codigo) {
    var form_div=document.getElementById("ovelay-mensaje-eliminar-estudiante");
    var form="<form id='mensaje-eliminar-estudiante' action='eliminar_estudiante.do' method='POST'>";
    
    form+="<input type='text' name='estudiante-a-eliminar' style='display:none;' value='"+codigo+"' required/>";
    form+="<p><b>Esta seguro de que desea eliminar al estudiante: "+codigo+"<b><p>";
    form+="<div class='botones'>";
    form+="<button type='submit'>Eliminar</button>";     
    form+="<button type='button' onclick='mensajeEliminarEstudianteOverlayOff()'>Cancelar</button>";
    form+="</div>";
    form+="</form>";
    form_div.innerHTML=form;
    document.getElementById("ovelay-mensaje-eliminar-estudiante").style.display = "block";
}

