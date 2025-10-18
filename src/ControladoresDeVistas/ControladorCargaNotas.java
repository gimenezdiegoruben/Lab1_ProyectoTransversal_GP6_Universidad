package ControladoresDeVistas;

import Modelos.Alumno;
import Modelos.Materia;
import Persistencias_Conexion.AlumnoData;
import Persistencias_Conexion.InscripcionData;
import Vistas.App_Menu;
import Vistas.VistaCargaNotas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/* 
    @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class ControladorCargaNotas implements ActionListener {

    public AlumnoData alumdata;
    public InscripcionData inscdata;
    public VistaCargaNotas vistacarganotas;
    public App_Menu menu;
    
    MyTableModel modelo = new MyTableModel(); //Utilizamos el modelo personalizado para permitir la edición de la columna de nota

    public ControladorCargaNotas(AlumnoData alumdata, InscripcionData inscdata, VistaCargaNotas vistacarganotas, App_Menu menu) {
        this.alumdata = alumdata;
        this.inscdata = inscdata;
        this.menu = menu;
        this.vistacarganotas = vistacarganotas;

        // Agregan un oyente de accion a los componentes de la interfaz de usuario
        vistacarganotas.jComboBListAlumCargaNotas.addActionListener(this);
        vistacarganotas.jButtonSalirCargaNotas.addActionListener(this);
        vistacarganotas.jButtonGuardar.addActionListener(this);

        ModeloTablaCargaNotas();
        cargarComboCargaNotas();
    }

    public void inicia() {
        //Establecemos el modelo de tabla personalizado para la edicion de celdas
        vistacarganotas.jTableCargaNotas.setModel(modelo); 
        
        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vistacarganotas);
        vistacarganotas.setVisible(true);
        menu.jFondo.moveToFront(vistacarganotas);
    }
    
    //Obtenemos el ID del alumno seleccionado en el Combo
    private int obtenerIdAlumnoSeleccionado() {
        Object selectedItemObj = vistacarganotas.jComboBListAlumCargaNotas.getSelectedItem();

        if (selectedItemObj != null && selectedItemObj instanceof String) {
            String selectedItem = (String) selectedItemObj;
            
            String[] partes = selectedItem.split(" - ");//formato DNI - Apellido, Nombre - ID
            if (partes.length >= 3) {
                try {
                    //El índice 2 contiene el ID del alumno
                    return Integer.parseInt(partes[2].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear el ID del alumno: " + e.getMessage());
                }
            }
        }
        return -1; //Retorna -1 si no hay item o hay error
    }

    //Limpiamos la tabla antes de cargar nuevos datos
    public void borrarFilas() {
        int a = modelo.getRowCount() - 1; 
        for (int i = a; i >= 0; i--) { 
            modelo.removeRow(i); 
        }
    }

    //Cargamos las materias inscriptas de un alumno a la tabla
    public void cargarTablaMaterias() {
        borrarFilas();
        int idAlumno = obtenerIdAlumnoSeleccionado();

        if (idAlumno > 0) {
            List<Materia> listaMaterias = inscdata.obtenerMateriasCursadas(idAlumno);

            if (listaMaterias.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El alumno no está inscripto en ninguna materia.");
            } else {
                for (Materia materia : listaMaterias) {
                    //El método notadeMateria de InscripcionData retorna un int que lo almacenamos como Object y lo convertimos a Double para el modelo de tabla
                    Object notaObj = inscdata.notadeMateria(idAlumno, materia.getIdMateria());
                    double notaDouble = 0.0;

                    if (notaObj != null) {
                        try {
                            //Convertimos el resultado de la base de datos (que es un Integer o int) a Double
                            if (notaObj instanceof Integer) {
                                notaDouble = ((Integer) notaObj).doubleValue();
                            } else {
                                //Por las dudas parseamos si notadeMateria fuera modificado para devolver otro tipo Number o String
                                notaDouble = Double.parseDouble(notaObj.toString());
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error al convertir nota de BD a Double: " + e.getMessage());
                            notaDouble = 0.0; //0.0 por defecto si falla la conversión
                        }
                    } 
                    
                    modelo.addRow(new Object[]{
                        materia.getIdMateria(),
                        materia.getNombre(),
                        notaDouble //Usamos el valor Double
                    });
                }
            }
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistacarganotas.jComboBListAlumCargaNotas) {
            cargarTablaMaterias();
        }

        if (e.getSource() == vistacarganotas.jButtonSalirCargaNotas) {
            vistacarganotas.dispose();
            menu.jFondo.repaint(); 
        }

        if (e.getSource() == vistacarganotas.jButtonGuardar) {
            int idAlumno = obtenerIdAlumnoSeleccionado();
            int filaSeleccionada = vistacarganotas.jTableCargaNotas.getSelectedRow();
            
            //Validaciones
            if (idAlumno <= 0) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un alumno válido.");
                return;
            }
            
            if (filaSeleccionada < 0) { 
                JOptionPane.showMessageDialog(null, "Debe seleccionar la fila de la calificación a guardar.");
                return;
            }

            //Extraemos datos de la fila seleccionada
            Object idMateriaObj = modelo.getValueAt(filaSeleccionada, 0); 
            Object notaObj = modelo.getValueAt(filaSeleccionada, 2);
            int idMateria = (int) idMateriaObj;
            double notad = 0.0;

            try {
                //El modelo de la tabla es Double.class, pero al editar Swing a menudo devuelve un String..esto maneja ambos casos
                if (notaObj == null) {
                    notad = 0.0;
                } else if (notaObj instanceof String) {
                    notad = Double.parseDouble(((String) notaObj).trim().replace(',', '.')); //Maneja comas y asegura trim
                } else if (notaObj instanceof Number) {
                    notad = ((Number) notaObj).doubleValue();
                } else {
                     //Intentamos un parseo final si no es un tipo conocido para asegurarnos
                    notad = Double.parseDouble(notaObj.toString().trim().replace(',', '.')); 
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: La calificación solo acepta números válidos");
                return;
            }

            //Validaciones de la nota
            if (notad < 1.0) { //Se asume como nota mínima sea 1.0
                JOptionPane.showMessageDialog(null, "La calificación debe ser al menos 1.0");
            } else if (notad > 10.0) {
                JOptionPane.showMessageDialog(null, "Solo se aceptan calificaciones del 1 al 10 (o 1.0 a 10.0");
            } else {
                // Actualización en la base de datos
                inscdata.actualizarNota(idAlumno, idMateria, notad);
                JOptionPane.showMessageDialog(null, "Se ha actualizado la calificación del alumno");

                cargarTablaMaterias(); 
            }
        }
    }

    public void ModeloTablaCargaNotas() {
        modelo.addColumn("Id Materia");
        modelo.addColumn("Nombre");
        modelo.addColumn("Nota");
        vistacarganotas.jTableCargaNotas.setModel(modelo); 
    }

    public void cargarComboCargaNotas() {
        List<Alumno> alumnos = alumdata.listarAlumnos();
        vistacarganotas.jComboBListAlumCargaNotas.removeAllItems();
        for (Alumno alumno : alumnos) {
            if (alumno.isEstado()) {
                String alumc = alumno.getDni() + " - " + alumno.getApellido() + ", " + alumno.getNombre() + " - " + alumno.getIdAlumno();
                vistacarganotas.jComboBListAlumCargaNotas.addItem(alumc);
            }
        }
    }
    
    //Clase interna para el modelado de tabla personal
    public class MyTableModel extends DefaultTableModel {

        //Para habilitar la modif de la tercer columna (índice 2) en jTableCargaNotas
        @Override
        public boolean isCellEditable(int row, int column) {
            
            return column == 2; //Solo la columna "Nota" es editable
        }

        //Definimos los tipos de datos para las columnas.
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: //Id Materia
                    return Integer.class;
                case 1: //Nombre
                    return String.class;
                case 2: //Nota
                    return Double.class; //Usamos Double para manejar la edición y la nota decimal
                default:
                    return Object.class;
            }
        }
    }
}