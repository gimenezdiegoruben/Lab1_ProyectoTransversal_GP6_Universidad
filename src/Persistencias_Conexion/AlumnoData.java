package Persistencias_Conexion;

import Modelos.Alumno;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author
 */
public class AlumnoData {

    private Connection con = null;

    public AlumnoData() {

    }

    public void guardarAlumno(Alumno alumno) {

        String sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        con = Conexion.getConexion();

        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento())); // Pasamos de LocalDate a Date
            ps.setBoolean(5, alumno.isEstado());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                // Obtener y asignar el ID autogenerado
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    alumno.setIdAlumno(rs.getInt(1));
                    JOptionPane.showMessageDialog(null, "Alumno" + alumno.getNombre() + "añadido con éxito");
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno: " + ex.getMessage());

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar el PreparedStatement: " + ex.getMessage());
            }
        }
    }

    public Alumno buscarAlumnoPorDni(int dni) {
        con = Conexion.getConexion();
        Alumno alumno = null;
        String sql = "SELECT * FROM alumno WHERE dni=?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe el alumno");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno " + ex.getMessage());
        }
        return alumno;
    }

    public List<Alumno> listarAlumnos() {
        con = Conexion.getConexion();
        List<Alumno> alumnos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM alumno WHERE estado = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnos.add(alumno);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error, no se pudo acceder a la tabla Alumno " + ex.getMessage());
        }
        return alumnos;
    }

    public void modificarAlumno(Alumno alumno) {
        con = Conexion.getConexion();
        String sql = "UPDATE alumno SET dni = ?, apellido = ?, nombre = ?, fechaNacimiento = ?, estado = ? WHERE idAlumno = ?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setBoolean(5, alumno.isEstado());
            ps.setInt(6, alumno.getIdAlumno());
            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Modificado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No hubo modificaciones. Error al acceder a la tabla Alumno " + ex.getMessage());
        }
    }

    public void eliminarAlumno(int id) {
        con = Conexion.getConexion();
        String sql = "UPDATE alumno SET estado = 0 WHERE idAlumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Se eliminó  el alumno con ID" + id + "(baja lógica)");
            } else {
                System.out.println("No se encontró el alumno con ID: " + id + " para dar de baja.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al borrar alumno. No se puede acceder a la tabla Alumno");
        }
    }
}
