package Modelo;

import Modelo.Estudiante;
import Modelo.Materia;
import Modelo.MateriaEstudiantePK;
import Modelo.Nota;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-18T08:35:26")
@StaticMetamodel(MateriaEstudiante.class)
public class MateriaEstudiante_ { 

    public static volatile SingularAttribute<MateriaEstudiante, Estudiante> estudiante;
    public static volatile SingularAttribute<MateriaEstudiante, MateriaEstudiantePK> materiaEstudiantePK;
    public static volatile SingularAttribute<MateriaEstudiante, Materia> materia;
    public static volatile ListAttribute<MateriaEstudiante, Nota> notaList;

}