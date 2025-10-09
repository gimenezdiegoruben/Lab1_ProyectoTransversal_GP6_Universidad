package ControladoresDeVistas;

import Modelos.Materia;
import Persistencias_Conexion.MateriaData;
import Vistas.App_Menu;
import Vistas.VistaMateria;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JOptionPane;

/*  @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class ControladorMateria implements ActionListener, FocusListener, KeyListener {

    private final VistaMateria vista;
    private final MateriaData data;
    private final App_Menu menu;

    private boolean buscar = false;

    public ControladorMateria(VistaMateria vista, MateriaData data, App_Menu menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);
        vista.jbtBuscar.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
    }

    public void iniciar() {
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus(); 
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        
        if (e.getSource() == vista.jbtSalir) {
            vista.dispose();
        }
        
        if (e.getSource() == vista.jbtNuevo) {
            activarCampos();
            limpiarCampos();
            vista.jbtGuardar.setEnabled(true);
            buscar = false;
        }

        if (e.getSource() == vista.jbtGuardar) {
            boolean activo;
            boolean repetido = false;
            if (vista.jchEstado.isSelected()) {
                activo = true;
            } else {
                activo = false;
            }
            if (vista.jtxCodigo.getText().trim().isEmpty() || vista.jtxNombre.getText().trim().isEmpty() || vista.jtxAño.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (buscar) {
                        Materia m1 = new Materia(Integer.parseInt(vista.jtxCodigo.getText().trim()), vista.jtxNombre.getText().trim(), Integer.parseInt(vista.jtxAño.getText().trim()), vista.jchEstado.isSelected());
                        Materia m2 = data.buscarMateria(Integer.parseInt(vista.jtxCodigo.getText().trim()));
                        data.modificarMateria(m1);
                        JOptionPane.showMessageDialog(null, "La materia " + m2.getNombre() + " " + m2.getAnioMateria() + (m2.isEstado() ? " Activo" : " Inactivo")
                                + " ha sido modificada a: " + m1.getNombre() + " " + m1.getAnioMateria() + (m2.isEstado() ? " Activo" : " Inactivo"));
                        buscar = false;
                        limpiarCampos();
                        vista.jbtEliminar.setEnabled(false);
                    } else {
                        Materia m1 = new Materia(Integer.parseInt(vista.jtxCodigo.getText().trim()), vista.jtxNombre.getText().trim(), Integer.parseInt(vista.jtxAño.getText().trim()), activo);
                        List<Materia> listaMaterias = data.listarMaterias();
                        for (Materia aux : listaMaterias) {
                            if (aux.getNombre().equals(m1.getNombre()) && aux.getAnioMateria() == m1.getAnioMateria()) {
                                JOptionPane.showMessageDialog(null, "La materia que intentas guardar ya ha sido creada anteriormente", "Materia repetida", JOptionPane.ERROR_MESSAGE);
                                limpiarCampos();
                                vista.jbtEliminar.setEnabled(false);
                                repetido = true;
                            }
                        }
                        if (!repetido) {
                            data.guardarMateria(m1);
                            JOptionPane.showMessageDialog(null, m1.getNombre() + " " + m1.getAnioMateria() + " añadida exitosamente", "Válido", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            vista.jbtEliminar.setEnabled(false);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número en los campos Código y Año", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == vista.jbtBuscar) {
            try {
                int id = Integer.parseInt(vista.jtxCodigo.getText().trim());
                Materia m1 = data.buscarMateria(id);
                if (m1 != null) {
                    buscar = true;
                    vista.jtxCodigo.setEditable(false);
                    vista.jtxCodigo.setText(String.valueOf(m1.getIdMateria()));
                    vista.jtxNombre.setText(m1.getNombre());
                    vista.jtxAño.setText(String.valueOf(m1.getAnioMateria()));
                    if (m1.isEstado()) {
                        vista.jchEstado.setSelected(true);
                    } else {
                        vista.jchEstado.setSelected(false);
                    }
                    activarCampos();
                    vista.jbtGuardar.setEnabled(true);
                    vista.jbtEliminar.setEnabled(true);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido en el código", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == vista.jbtEliminar) {
            try {
                int id = Integer.parseInt(vista.jtxCodigo.getText().trim());
                Materia m1 = data.buscarMateria(id);
                if (m1 != null) {
                    data.eliminarMateria(id);
                    JOptionPane.showMessageDialog(null, "Materia " + m1.getNombre() + " " + m1.getAnioMateria() + " eliminada correctamente", "Válido", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    vista.jbtEliminar.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido en el campo Código", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void activarCampos() {
        vista.jtxCodigo.setEditable(true);
        vista.jtxNombre.setEditable(true);
        vista.jtxAño.setEditable(true);
        vista.jchEstado.setEnabled(true);
    }

    public void desactivarCampos() {
        vista.jtxCodigo.setEditable(false);
        vista.jtxNombre.setEditable(false);
        vista.jtxAño.setEditable(false);
        vista.jchEstado.setEnabled(false);
    }

    public void limpiarCampos() {
        vista.jtxCodigo.setText("");
        vista.jtxNombre.setText("");
        vista.jtxAño.setText("");
        vista.jchEstado.setSelected(false);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}