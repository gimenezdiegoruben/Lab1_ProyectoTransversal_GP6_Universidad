package ControladoresDeVistas;

import Persistencias_Conexion.AlumnoData;
import Vistas.App_Menu;
import Vistas.VistaAlumnos;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Connection;

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
        vista.requestFocus();
        vista.jtxDocumento.setText("0");
        vista.jbtGuardar.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // botones Buscar, Nuevo, Guardar, Eliminar, Salir
    }
    
    // Métodos requeridos por KEYLISTENER
    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        // para validar la entrada de datos 
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
