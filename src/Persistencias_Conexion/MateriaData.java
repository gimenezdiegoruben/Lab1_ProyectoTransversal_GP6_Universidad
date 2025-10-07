package Persistencias_Conexion;

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
    @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
**/

public class MateriaData {

    private Connection conexion;

    public MateriaData() {
    }

    public void guardarMateria(Materia materia) {
        try {
            conexion = Conexion.getConexion();
            String sql = "INSERT INTO materia (nombre, año, estado) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, materia.getNombre());
            statement.setInt(2, materia.getAnio());
            statement.setBoolean(3, materia.isEstado());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion();
    }

    public Materia buscarMateria(int id) {
        Materia materia = null;
        try {
            conexion = Conexion.getConexion();
            String sql = "SELECT * FROM materia WHERE idMateria = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                materia = new Materia(
                        resultSet.getString("nombre"),
                        resultSet.getInt("año"),
                        resultSet.getBoolean("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cerrarConexion();
        return materia;
    }

    public void modificarMateria(Materia materia) {
        try {
            conexion = Conexion.getConexion();
            String sql = "UPDATE materia SET nombre = ?, año = ?, estado = ? WHERE idMateria = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, materia.getNombre());
            statement.setInt(2, materia.getAnio());
            statement.setBoolean(3, materia.isEstado());
            statement.setInt(4, materia.getIdMateria());
            statement.executeUpdate();
        } catch (SQLException e) {

        }
        cerrarConexion();
    }

    public void eliminarMateria(int id) {
        try {
            conexion = Conexion.getConexion();
            String sql = "DELETE FROM materia WHERE idMateria = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000") && e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(null, "Existen alumnos inscriptos en esta materia. Para eliminar la materia no deben existir alumnos inscriptos!");

            } else {
                JOptionPane.showMessageDialog(null, "error: " + e.getMessage());
            }
        }
        cerrarConexion();
    }

    public List<Materia> listarMaterias() {
        List<Materia> materias = new ArrayList<>();
        try {
            conexion = Conexion.getConexion();
            String sql = "SELECT * FROM materia";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Materia materia = new Materia(
                        resultSet.getString("nombre"),
                        resultSet.getInt("año"),
                        resultSet.getBoolean("estado")
                );
                materias.add(materia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cerrarConexion();
        return materias;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}