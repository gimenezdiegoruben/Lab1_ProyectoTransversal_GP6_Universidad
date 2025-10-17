/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencias_Conexion;

import Modelos.Alumno;
import Modelos.Inscripcion;
import Modelos.Materia;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 *  @author Ger
 *  Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class InscripcionData {
    private Connection conexion;
    private MateriaData materiaData = new MateriaData();
    private AlumnoData alumnoData = new AlumnoData();
    public InscripcionData(){
    }
    
    public void guardarInscripcion(Inscripcion inscripcion){
        try {
            conexion= Conexion.getConexion();
            String sql="INSERT INTO inscripcion ( nota, idAlumno, idMateria) VALUES (?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setDouble(1, inscripcion.getNota());
            statement.setInt(2,inscripcion.getAlumno().getIdAlumno());
            statement.setInt(3, inscripcion.getMateria().getIdMateria());
            int filasAfectadas= statement.executeUpdate();
            if(filasAfectadas>0){
                JOptionPane.showMessageDialog(null, "Se inscribio al alumno " + inscripcion.getAlumno().getNombre() + " a la materia " + inscripcion.getMateria().getNombre() + " correctamente!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar inscripcion"+ ex);
        }
    }
    
    public Inscripcion buscarInscripcion(int dniAlumno,int idMateria){
        Inscripcion inscripcion= null;
        try {
            conexion=Conexion.getConexion();
            String sql="SELECT * FROM materia WHERE dni= ? AND idAlumno=?";
            PreparedStatement statement=conexion.prepareStatement(sql);
            statement.setInt(1,dniAlumno);
            statement.setInt(2,idMateria);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                int idInscripcion= resultSet.getInt("idInscripcion");
                double nota= resultSet.getDouble("nota");
                Alumno alumno= alumnoData.buscarAlumnoPorDni(dniAlumno);
                Materia materia= materiaData.buscarMateria(idMateria);
                
                inscripcion = new Inscripcion(idInscripcion,nota,alumno, materia);
               
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la inscripcion " + ex.getMessage());
        }
        return inscripcion;
    }
    
    
    public List<Inscripcion> obtenerInscripciones(){
    conexion= Conexion.getConexion();
    ArrayList<Inscripcion> inscripciones= new ArrayList<>();
    String sql = "SELECT * FROM inscripcion";      
        try {
            PreparedStatement ps= conexion.prepareStatement(sql);
            ResultSet resultSet= ps.executeQuery();
            
            while(resultSet.next()){
            Inscripcion inscripcion= new Inscripcion();
            Materia materia = materiaData.buscarMateria(resultSet.getInt("idMateria"));
            Alumno alumno = alumnoData.buscarAlumnoPorDni(resultSet.getInt("idAlumno"));
            inscripcion.setIdInscripto(resultSet.getInt("idInscripto"));
            inscripcion.setNota(resultSet.getDouble("nota"));
            inscripcion.setMateria(materia);
            inscripcion.setAlumno(alumno);

            inscripciones.add(inscripcion);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error al acceder a  la tabla de inscripciones " + ex.getMessage());
        }
        return inscripciones;
}
    
    
    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id) {
        conexion = Conexion.getConexion();
        ArrayList<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT idInscripto, idMateria FROM inscripcion WHERE idAlumno = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripto(resultSet.getInt("idInscripto"));
                Materia materia = materiaData.buscarMateria(resultSet.getInt("idMateria"));
                Alumno alumno = alumnoData.buscarAlumnoPorDni(resultSet.getInt("idAlumno"));
                inscripcion.setMateria(materia);
                inscripcion.setAlumno(alumno);

                inscripciones.add(inscripcion);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar inscripciones por Id de alumno" + ex.getMessage());
        }
        return inscripciones;
    }
    
    public List<Materia> obtenerMateriasCursadas(int id) {
        conexion = Conexion.getConexion();
        List<Materia> materiasCursadas = new ArrayList<>();
        String sql = "SELECT inscripcion.idMateria, materia.nombre, materia.año FROM inscripcion,materia WHERE inscripcion.idMateria=materia.idMateria AND inscripcion.idAlumno = ? AND materia.estado=1";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(resultSet.getInt("idMateria"));
                materia.setNombre(resultSet.getString("nombre"));
                materia.setAnioMateria(resultSet.getInt("año"));
                materiasCursadas.add(materia);
            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se encontraron materias cursadas " + ex.getMessage());
        }
        return materiasCursadas;
    }

    public List<Materia> obtenerMateriasNoCursadas(int id) {
        conexion = Conexion.getConexion();
        ArrayList<Materia> materiasNoCursadas = new ArrayList<>();
        String sql = "SELECT materia.idMateria, nombre, año FROM materia "
                + "WHERE materia.idMateria NOT IN (SELECT inscripcion.idMateria FROM inscripcion"
                + " WHERE inscripcion.idAlumno = ? AND materia.estado=1)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(resultSet.getInt("idMateria"));
                materia.setNombre(resultSet.getString("nombre"));
                materia.setAnioMateria(resultSet.getInt("año"));
                materiasNoCursadas.add(materia);
            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias no cursadas: " + ex.getMessage());
        }
        return materiasNoCursadas;
    }

    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria) {
        conexion = Conexion.getConexion();
        try {
            String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idAlumno);
                ps.setInt(2, idMateria);

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión no se pudo borrar el registro " + ex.getMessage());
        }
    }

    public void actualizarNota(int idAlumno, int idMateria, double nota) {
        conexion = Conexion.getConexion();
        try {

            String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? AND idMateria = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            {
                ps.setDouble(1, nota);
                ps.setInt(2, idAlumno);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo actualizar el alumno " + ex.getMessage());
        }
    }

    public List<Alumno> obtenerAlumnosxMateria(int idMateria) {
        conexion = Conexion.getConexion();
        List<Alumno> alumnosInscritos = new ArrayList<>();
        try {

            String sql = "SELECT alumno.idAlumno, alumno.nombre, alumno.dni, alumno.apellido FROM Alumno JOIN inscripcion ON alumno.idAlumno = inscripcion.idAlumno WHERE inscripcion.idMateria = ? AND alumno.estado=1";

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idMateria);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultSet.getInt("idAlumno"));
                alumno.setNombre(resultSet.getString("nombre"));
                alumno.setDni(resultSet.getInt("dni"));
                alumno.setApellido(resultSet.getString("apellido"));
                alumnosInscritos.add(alumno);
            }
            cerrarRecursos(ps, resultSet);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo obtener la materia del alumno solicitado " + ex.getMessage());// Manejo de excepciones
        }
        return alumnosInscritos;
    }

    private void cerrarRecursos(PreparedStatement ps, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    public Object notadeMateria(int idAlumno, int idMateria) {
        int Nota = 0;
        try {
            conexion = Conexion.getConexion();
            String sql = "SELECT * FROM inscripcion WHERE idAlumno=? AND idMateria=?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Nota = resultSet.getInt("nota");
            }

        } catch (SQLException ex) {
            Logger.getLogger(InscripcionData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Object) Nota;
    }
}    
    
