package ControladoresDeVistas;

import Modelos.Alumno;
import Persistencias_Conexion.AlumnoData;
import Vistas.App_Menu;
import Vistas.VistaAlumnos;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;

/*  @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class ControladorAlumnos implements ActionListener, KeyListener {

    private Connection con;
    private final VistaAlumnos vista;
    private final AlumnoData data;
    private final App_Menu menu;
    private int idAlumno;

    public ControladorAlumnos(VistaAlumnos vista, AlumnoData data, App_Menu menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;
        vista.jtxDocumento.addKeyListener(this);
        vista.jbtBuscar.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);
        vista.jrbEstado.addActionListener(this);
    }

    public void iniciar() {
        this.menu.jFondo.add(vista); // Agrega la vista (JInternalFrame) al JDesktopPane
        this.vista.setVisible(true);
        this.menu.jFondo.moveToFront(vista);
        vista.requestFocus();
        vista.jtxDocumento.setText("0");
        vista.jbtGuardar.setEnabled(false);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == vista.jbtBuscar) {
            if (vista.jtxDocumento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El Dni no puede estar en blanco");
            } else {
                int a = Integer.parseInt(vista.jtxDocumento.getText());
                Alumno alum = new Alumno();
                alum = data.buscarAlumnoPorDni(a);
                if (alum != null) {
                    vista.jtxNombre.setText(alum.getNombre());
                    vista.jtxApellido.setText(alum.getApellido());
                    vista.jrbEstado.setSelected(alum.isEstado());
                    vista.jdcFechadeNacimiento.setDate(Date.valueOf(alum.getFechaNacimiento()));
                    this.idAlumno = alum.getIdAlumno();
                    vista.jbtGuardar.setEnabled(true);
                    vista.jbtEliminar.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El alumno no existe");
                    this.idAlumno = -1;
                }
            }
        }

        if (e.getSource() == vista.jbtEliminar) {
            int dni = Integer.parseInt(vista.jtxDocumento.getText());
            if (dni > 0) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar al alumno?");

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // El usuario confirmó eliminar, procede a eliminar el alumno
                    data.eliminarAlumno(dni);
                    JOptionPane.showMessageDialog(null, "Alumno eliminado con éxito.");

                    // Luego puedes limpiar los campos de la vista si lo deseas
                    vista.jtxDocumento.setText("");
                    vista.jtxNombre.setText("");
                    vista.jtxApellido.setText("");
                    vista.jrbEstado.setSelected(true);
                    vista.jdcFechadeNacimiento.setDate(null);
                    data.eliminarAlumno(this.idAlumno);
                } else {
                    // El usuario canceló la eliminación, no hace nada
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar un alumno con un DNI inválido.");
            }
        }

        if (e.getSource() == vista.jbtSalir) {
            vista.dispose();
        }
        if (e.getSource() == vista.jbtNuevo) {
            vista.jbtNuevo.setEnabled(false);
            vista.jbtEliminar.setEnabled(false);
            vista.jtxDocumento.setText("0");
            vista.jtxNombre.setText("");
            vista.jtxApellido.setText("");
            vista.jtxDocumento.requestFocus();
            vista.jbtGuardar.setEnabled(true);
            this.idAlumno = -1;

        }
        if (e.getSource() == vista.jbtGuardar) {
            vista.jbtNuevo.setEnabled(true);
            vista.jbtEliminar.setEnabled(true);
            vista.jtxDocumento.setEnabled(true);

            if (idAlumno == 0 && vista.jtxDocumento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El número de documento no puede ser cero. Ingrese un valor válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int dni = Integer.parseInt(vista.jtxDocumento.getText());
                String nombre = vista.jtxNombre.getText();
                String apellido = vista.jtxApellido.getText();
                boolean estado = true; // Asumiendo que siempre quieres establecer el estado en verdadero
                java.util.Date nac = vista.jdcFechadeNacimiento.getDate();

                // Convierte el objeto Date a Instant
                Instant instant = nac.toInstant();

                // Convierte el Instant a LocalDate utilizando una zona horaria específica
                LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                if (idAlumno == -1) {
                    Alumno a = new Alumno(dni, apellido, nombre, fecha, estado);
                    // Código para guardar el alumno existente
                    // Pregunta al usuario si quiere guardar el nuevo alumno
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de guardar el nuevo alumno?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        data.guardarAlumno(a);
                        JOptionPane.showMessageDialog(null, "Alumno guardado con éxito.");
                    }
                } else {
                    // Código para modificar un nuevo alumno
                    Alumno b = new Alumno(idAlumno, dni, apellido, nombre, fecha, true);
                    data.modificarAlumno(b);
                    JOptionPane.showMessageDialog(null, "Se ha guardado la modificación.");
                }
            }
        }
    }

    // Métodos requeridos por KEYLISTENER
    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        if (e.getSource() == vista.jtxDocumento) {
            char caracter = e.getKeyChar();
            if (caracter < '0' || caracter > '9') {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        // No se usa
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        // No se usa
    }
}
