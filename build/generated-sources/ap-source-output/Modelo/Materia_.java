package Modelo;

import Modelo.MateriaEstudiante;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-18T08:35:25")
@StaticMetamodel(Materia.class)
public class Materia_ { 

    public static volatile SingularAttribute<Materia, String> codigo;
    public static volatile ListAttribute<Materia, MateriaEstudiante> materiaEstudianteList;
    public static volatile SingularAttribute<Materia, String> nombre;

}