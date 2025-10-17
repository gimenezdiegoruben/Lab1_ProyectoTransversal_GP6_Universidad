package ControladoresDeVistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import Persistencias_Conexion.AlumnoData;
import Persistencias_Conexion.InscripcionData;
import Modelos.Alumno;
import Modelos.Inscripcion;
import Modelos.Materia;
import Vistas.VistaInscripcion;
import Vistas.App_Menu;

/*  @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class ControladorInscripciones implements ActionListener, ListSelectionListener {

    private final VistaInscripcion vista;
    private final AlumnoData alumnoData;
    private final InscripcionData inscripcionData;
    private final App_Menu menu;
    DefaultTableModel modelo = new DefaultTableModel();

    private List<Materia> materiasDisponibles;
    private List<Materia> materiasInscriptas;
    private Alumno alumnoSeleccionado;

    public ControladorInscripciones(VistaInscripcion vista, AlumnoData alumnoData, InscripcionData inscripcionData, App_Menu menu) {
        this.vista = vista;
        this.alumnoData = alumnoData;
        this.inscripcionData = inscripcionData;
        this.menu = menu;

        this.vista.jbtInscribir.addActionListener(this);
        this.vista.jbtAnularInscripcion.addActionListener(this);
        this.vista.jbtSalir.addActionListener(this);
        this.vista.jComboBox1.addActionListener(this);
        this.vista.jRadioButtonMateriasInscriptas.addActionListener(this);
        this.vista.jRadioButtonMateriasNoInscriptas.addActionListener(this);
        this.vista.jTable1.getSelectionModel().addListSelectionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Inscripciones");

        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus();
        rellenarCombo();
        modelaTabla();
        vista.jRadioButtonMateriasInscriptas.setSelected(true);
        rellenarTabla();
        vista.jbtInscribir.setEnabled(false);

        ClassLoader directorio = getClass().getClassLoader();
        URL SalirIconUbicacion = directorio.getResource("Icons/icon_salida.png");
        ImageIcon SalirIcono = new ImageIcon(SalirIconUbicacion);
        vista.jbtSalir.setIcon(SalirIcono);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.jComboBox1) {
            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasInscriptas) {
            vista.jbtInscribir.setEnabled(false);
            vista.jbtAnularInscripcion.setEnabled(true);
            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasNoInscriptas) {

            vista.jbtInscribir.setEnabled(true);
            vista.jbtAnularInscripcion.setEnabled(false);
            rellenarTabla();
        }

        if (e.getSource() == vista.jbtInscribir) {

            if (vista.jTable1.getSelectedRow() != -1) {

                Inscripcion inscribir = new Inscripcion();
                Materia materiasNueva = new Materia();
                Alumno alumn = new Alumno();

                int filaSelect = vista.jTable1.getSelectedRow();
                int idMateriaSelect = (int) modelo.getValueAt(filaSelect, 0);

                materiasNueva.setIdMateria(idMateriaSelect);
                alumn.setIdAlumno(traerID());

                inscribir.setAlumno(alumn);
                inscribir.setMateria(materiasNueva);
                inscribir.setNota(0);

                inscripcionData.guardarInscripcion(inscribir);
                rellenarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "El alumno ya está inscrito en esta materia.");
            }
        }

        if (e.getSource() == vista.jbtSalir) {
            vista.dispose();
        }

        if (e.getSource() == vista.jbtAnularInscripcion) {
            anularInscripcionAlumno();
            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasInscriptas) {
            vista.jRadioButtonMateriasNoInscriptas.setSelected(false);
            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasNoInscriptas) {
            vista.jRadioButtonMateriasInscriptas.setSelected(false);
            rellenarTabla();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    public void modelaTabla() {
        modelo.addColumn("Id Materia");
        modelo.addColumn("Materia");
        modelo.addColumn("Año");

        vista.jTable1.setModel(modelo);
    }

    private void rellenarCombo() {
        List<Alumno> alumnos = new ArrayList<>();

        alumnos = alumnoData.listarAlumnos();
        vista.jComboBox1.removeAllItems();

        for (Alumno alumno : alumnos) {
            if (alumno.isEstado()) {
                String cadena = alumno.getIdAlumno() + " - " + alumno.getDni() + " - " + alumno.getApellido();
                vista.jComboBox1.addItem(cadena);
            }
        }
    }

    private void cargarMateriasCursadas() {
        materiasInscriptas = inscripcionData.obtenerMateriasCursadas(alumnoSeleccionado.getIdAlumno());
        actualizarTablaConMaterias(materiasInscriptas);
    }

    private void cargarMateriasNoCursadas() {

        materiasDisponibles = inscripcionData.obtenerMateriasNoCursadas(alumnoSeleccionado.getIdAlumno());
        actualizarTablaConMaterias(materiasDisponibles);

        List<Materia> materiasDisponibles = inscripcionData.obtenerMateriasNoCursadas(alumnoSeleccionado.getIdAlumno());
        modelo.setRowCount(0);
        vista.jTable1.setModel(modelo);
    }

    private void actualizarTablaConMaterias(List<Materia> materias) {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable1.getModel();
        modelo.setRowCount(0);

        for (Materia materia : materias) {
            modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnioMateria()});
        }
    }

    private void rellenarTabla() {

        List<Materia> materias = new ArrayList<Materia>();
        if (vista.jRadioButtonMateriasInscriptas.isSelected()) {
            materias = inscripcionData.obtenerMateriasCursadas(traerID());
        } else {
            materias = inscripcionData.obtenerMateriasNoCursadas(traerID());
        }
        modelo.setRowCount(0);
        for (Materia materia : materias) {
            modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnioMateria()});
        }

        vista.jTable1.setModel(modelo);
    }

    private int traerID() {
        // Obtenemos el elemento seleccionado, que podría ser null.
        Object selectedItem = vista.jComboBox1.getSelectedItem(); 

        if (selectedItem != null) { // <--- VERIFICACIÓN CRÍTICA
            String varTemp = selectedItem.toString(); // Llamamos a toString() solo si no es nulo
            String[] partes = varTemp.split("-");
            // Aseguramos que la cadena tenga el formato esperado y que la primera parte sea un número
            try {
                int idAlumno = Integer.parseInt(partes[0].trim());
                return idAlumno;
            } catch (NumberFormatException e) {
                // Manejar el caso donde el ID no es un número (poco probable si rellenarCombo es correcto)
                JOptionPane.showMessageDialog(null, "Error al obtener el ID del alumno: " + e.getMessage());
                return -1; 
            }
        } else {
            // Si el ítem es nulo, devolvemos un ID inválido (como -1 o 0) para que 
            // los métodos que lo llaman (como rellenarTabla) no intenten acceder a la DB.
            return -1; 
        }
    }

    private Materia obtenerMateriaSeleccionada(int fila) {
        int idMateria = (int) vista.jTable1.getValueAt(fila, 0);
        return new Materia();
    }

    private void anularInscripcionAlumno() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada != -1) {

            int filaSelect = vista.jTable1.getSelectedRow();
            int idMateriaSelect = (int) modelo.getValueAt(filaSelect, 0);
            int idAlumno = traerID();

            inscripcionData.borrarInscripcionMateriaAlumno(idAlumno, idMateriaSelect);

            JOptionPane.showMessageDialog(null, "Inscripción anulada con éxito.");
        }
    }
}