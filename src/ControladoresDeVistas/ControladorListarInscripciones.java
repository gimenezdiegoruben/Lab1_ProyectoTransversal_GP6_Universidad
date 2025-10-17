package ControladoresDeVistas;

import Modelos.Alumno;
import Modelos.Materia;
import Persistencias_Conexion.InscripcionData;
import Persistencias_Conexion.MateriaData;
import Vistas.App_Menu;
import Vistas.VistaListarInscripciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*  @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */

public class ControladorListarInscripciones implements ActionListener {

    public MateriaData mdata;
    public InscripcionData idata;
    public VistaListarInscripciones vista;
    public App_Menu menu;
    
    DefaultTableModel modelo = new DefaultTableModel();

    public ControladorListarInscripciones(MateriaData mdata, InscripcionData idata, VistaListarInscripciones vista, App_Menu menu ) {
        this.mdata = mdata;
        this.idata = idata;
        this.menu = menu;
        this.vista = vista;

        vista.jcbMateria.addActionListener(this);
        vista.jbtSalir.addActionListener(this);

    }

    public void inicia() {
        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus();
        cargaCombo();
        modelaTabla();
        vista.jTabla.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jbtSalir) {
            vista.dispose();
        }
        if (e.getSource() == vista.jcbMateria) { // Actualiza la Tabla con los datos de la consulta de InscripcionData
            int idMateria = extraerIdMateria();
            List<Alumno> alumnos = new ArrayList<Alumno>();
            alumnos = idata.obtenerAlumnosxMateria(idMateria);
            modelo.setRowCount(0); // Borra todas las filas
            for (Alumno alumno : alumnos) {
                modelo.addRow(new Object[]{alumno.getIdAlumno(), alumno.getDni(), alumno.getApellido(), alumno.getNombre()});
            }
            vista.jTabla.setModel(modelo);
        }

    }

    private int extraerIdMateria() {
        int idMateria = -1;
        try {
            String combobox = vista.jcbMateria.getSelectedItem().toString();
            String partes[] = combobox.split("-");
            idMateria = Integer.parseInt(partes[0].trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "A ocurrido un error al cargar los indices en el combobox, revices la posicion del idMateria");
        }

        return idMateria;
    }

    public void modelaTabla() {
        modelo.addColumn("ID Alumno");
        modelo.addColumn("DNI");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        vista.jTabla.setModel(modelo);
    }

    public void cargaCombo() {
        List<Materia> materias = new ArrayList<Materia>();
        materias = mdata.listarMaterias();
        vista.jcbMateria.removeAllItems();
        for (Materia materia : materias) {
            if (materia.isEstado()) {
                String cadena = materia.getIdMateria() + " - " + materia.getNombre() + " de " + materia.getAnioMateria() + " año.";
                vista.jcbMateria.addItem(cadena);
            }
        }
    }

    public class MyTableModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 2; 
        }
    }
}