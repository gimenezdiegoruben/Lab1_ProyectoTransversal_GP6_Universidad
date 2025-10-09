/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencias_Conexion;

import Modelos.Alumno;
import Modelos.Inscripcion;
import Modelos.Materia;
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
    
    public InscripcionData(){
    }
    
    public void guardarInscripcion(Inscripcion inscripcion){
        try {
            conexion= Conexion.getConexion();
            String sql="INSERT INTO inscripcion ( nota, idAlumno, idMateria) VALUES (?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setDouble(1, inscripcion.getNota());
            statement.setInt(2,inscripcion.getAlumno().getIdAlumno());
            statement.setInt(3, inscripcion.getMateria().getIdMateria());
            int filasAfectadas= statement.executeUpdate();
            if(filasAfectadas>0){
                JOptionPane.showMessageDialog(null, "Se inscribio al alumno" + inscripcion.getAlumno().getNombre() + "a la materia" + inscripcion.getMateria().getNombre() + "correctamente!");
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
                int nota= resultSet.getInt("nota");
                
                AlumnoData alumnoData= new AlumnoData();
                MateriaData materiaData=new MateriaData();
                
                Alumno alumno= alumnoData.buscarAlumnoPorDni(dniAlumno);
                Materia materia= materiaData.buscarMateria(idMateria);
                
                inscripcion = new Inscripcion(idInscripcion,nota,alumno, materia);
               
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la inscripcion " + ex.getMessage());
        }
        return inscripcion;
    }
    
   // public void modificarInscripcion(Inscripcion inscripcion){}
    
   // public void eliminarInscripcion(int id){}
    
   // public List<Inscripcion> listarInscripciones(){}
    
    
}