package Modelo;

import Modelo.MateriaEstudiante;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-18T08:35:26")
@StaticMetamodel(Estudiante.class)
public class Estudiante_ { 

    public static volatile SingularAttribute<Estudiante, String> codigo;
    public static volatile SingularAttribute<Estudiante, String> password;
    public static volatile ListAttribute<Estudiante, MateriaEstudiante> materiaEstudianteList;
    public static volatile SingularAttribute<Estudiante, String> telefono;
    public static volatile SingularAttribute<Estudiante, String> nombre;
    public static volatile SingularAttribute<Estudiante, String> email;

}