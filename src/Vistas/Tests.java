
package Vistas;

import Modelos.Alumno;
import Persistencias_Conexion.AlumnoData;
import Persistencias_Conexion.Conexion;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class Tests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = Conexion.getConexion();
        if (con != null) {
            System.out.println("Conectado a la base de datos");
        } else {
            System.out.println("Error en la conexión");
            return;
        }
        AlumnoData alumnoData = new AlumnoData();

        System.out.println("--- Miembros del grupo ---");
        Alumno a1 = new Alumno(30551131, "Gimenez", "Diego", LocalDate.of(1983, 12, 13), true);
        alumnoData.guardarAlumno(a1);
        a1 = new Alumno(44309664, "Mecias Giacomelli", "Carlos", LocalDate.of(2002, 8, 5), true);
        alumnoData.guardarAlumno(a1);
        a1 = new Alumno(45802941, "Migliozzi Badani", "Tomas", LocalDate.of(2004, 8, 5), true);
        alumnoData.guardarAlumno(a1);
        a1 = new Alumno(46260667, "Urbani", "José María", LocalDate.of(2005, 2, 10), true);
        alumnoData.guardarAlumno(a1);
        System.out.println("\n--- Listado Completo de Alumnos Activos ---");
        List<Alumno> listaAlumnos = alumnoData.listarAlumnos();

        if (listaAlumnos.isEmpty()) {
            System.out.println("La bd no contiene alumnos, realiza el INSERT");
        } else {
            for (Alumno aux : listaAlumnos) {
                System.out.println("ID: " + aux.getIdAlumno()
                        + " | DNI: " + aux.getDni()
                        + " | Nombre: " + aux.getNombre()
                        + " " + aux.getApellido()
                        + " | Nacimiento: " + aux.getFechaNacimiento()
                        + " | Estado: " + (aux.isEstado() ? "ACTIVO" : "INACTIVO"));
            }
        }

        // Buscamos un alumno por DNI
        System.out.println("Buscando alumno por DNI...");
        Alumno a2 = alumnoData.buscarAlumnoPorDni(46260667);
        System.out.println(a2.toString());
        System.out.println("Dando de baja un alumno...");

        alumnoData.eliminarAlumno(84);

        listaAlumnos = alumnoData.listarAlumnos();

        for (Alumno aux : listaAlumnos) {
            System.out.println(aux.toString());
        }
        
    }
    
}
