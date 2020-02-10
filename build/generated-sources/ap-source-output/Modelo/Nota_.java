package Modelo;

import Modelo.MateriaEstudiante;
import Modelo.NotaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-18T08:35:26")
@StaticMetamodel(Nota.class)
public class Nota_ { 

    public static volatile SingularAttribute<Nota, MateriaEstudiante> materiaEstudiante;
    public static volatile SingularAttribute<Nota, Double> nota;
    public static volatile SingularAttribute<Nota, NotaPK> notaPK;

}