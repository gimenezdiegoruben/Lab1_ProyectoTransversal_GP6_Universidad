package Vistas;

import Modelos.Alumno;
import Modelos.Materia;
import Persistencias_Conexion.AlumnoData;
import Persistencias_Conexion.Conexion;
import Persistencias_Conexion.MateriaData;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/** 
    @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
**/

public class Tests {

   public static void main(String[] args) {

        // Test de Conexión
        Connection con = Conexion.getConexion();
        if (con != null) {
            System.out.println("Conectado a la base de datos.");
        } else {
            System.err.println("Error en la conexión.");
            return;
        }
        // 1 Instanciamos la clase de acceso a datos
        AlumnoData alumnoData = new AlumnoData();

        // 2 Insertamos integrantes del grupo
        System.out.println("****** Alumnos del Grupo ********");

        Alumno alumno1 = new Alumno(30551131, "Gimenez", "Diego", LocalDate.of(1983, 12, 13), true);
        alumnoData.guardarAlumno(alumno1); // lo guardamos y se le asigna un ID autom

        Alumno alumno2 = new Alumno(44309664, "Mecias Giacomelli", "Carlos", LocalDate.of(2002, 7, 23), true);
        alumnoData.guardarAlumno(alumno2);

        Alumno alumno3 = new Alumno(45802941, "Migliozzi Badani", "Tomas", LocalDate.of(2004, 8, 5), true);
        alumnoData.guardarAlumno(alumno3);

        Alumno alumno4 = new Alumno(46260667, " Ubani", "José", LocalDate.of(2005, 02, 10), true);
        alumnoData.guardarAlumno(alumno4);

        // 3 Mostramops todos los alumnos ingresados.
        System.out.println("\n****** Listado Completo de Alumnos Activos ********");
        List<Alumno> listaAlumnos = alumnoData.listarAlumnos();

        if (listaAlumnos.isEmpty()) {
            System.out.println(" La bd no contiene alumnos realiza el INSERT");
        } else {
            for (Alumno alumno : listaAlumnos) {
                System.out.println("ID: " + alumno.getIdAlumno()
                        + " | DNI: " + alumno.getDni()
                        + " | Nombre: " + alumno.getNombre()
                        + " " + alumno.getApellido()
                        + " | Nacimiento: " + alumno.getFechaNacimiento()
                        + " | Estado: " + (alumno.isEstado() ? "ACTIVO" : "INACTIVO"));
            }
        }
        // 4  Probamos el método bajaLogica/eliminarAlumno 
        System.out.println("\n\"******* Dando de baja un alumno...********");

        // Usamos el dni de alumno 4 para eliminar
        int dniParaEliminar = alumno4.getDni();
        alumnoData.eliminarAlumnoPorDni(dniParaEliminar);

        System.out.println("\n*******Listado tras la Baja Lógica (el alumno con dni: " + dniParaEliminar + " ya no deberia estar en la bd) **********");
        for (Alumno aux : alumnoData.listarAlumnos()) {
            System.out.println("ID: " + aux.getIdAlumno() + " | Nombre: " + aux.getNombre() + " " + aux.getApellido());
        }

        // Buscamos un alumno por DNI
        System.out.println("Buscando alumno por DNI...");
        Alumno a2 = alumnoData.buscarAlumnoPorDni(46260667);
        System.out.println(a2.toString());
        
        // PRUEBAS DE MATERIA
        System.out.println("\n**** Insertando Materias ****");

        // Instanciamos MateriaData
        MateriaData materiaData = new MateriaData();

        // Usamos el constructor con idMateria
        Materia materia1 = new Materia(0, "Lab Programación", 1, true); 
        materiaData.guardarMateria(materia1);

        Materia materia2 = new Materia(0, "Matemáticas", 2, true); 
        materiaData.guardarMateria(materia2);

        Materia materia3 = new Materia(0, "Base de datos", 1, false);
        materiaData.guardarMateria(materia3);

        // Listamos las materias insertadas
        System.out.println("\n*** Listado Completo de Materias (Todas) ***");
        List<Materia> listaMaterias = materiaData.listarMaterias();

        if (listaMaterias.isEmpty()) {
            System.out.println(" La bd no contiene materias.");
        } else {
            for (Materia materia : listaMaterias) {
                System.out.println("ID: " + materia.getIdMateria()
                        + " | Nombre: " + materia.getNombre()
                        + " | Año: " + materia.getAnioMateria()
                        + " | Estado: " + (materia.isEstado() ? "MATERIA ACTIVA" : "MATERIA INACTIVA"));
            }
        }
    }
}