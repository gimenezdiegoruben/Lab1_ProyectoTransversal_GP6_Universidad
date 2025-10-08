package ControladoresDeVistas;

import Persistencias_Conexion.AlumnoData;
import Vistas.VistaAlumnos;
import Vistas.App_Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/*  @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */

public class ControladorApp_Menu implements ActionListener, MenuListener, ComponentListener {

    private final App_Menu menu;

    public ControladorApp_Menu(App_Menu menu) {
        this.menu = menu;

        this.menu.jmiFormularioAlumno.addActionListener(this);

        // escucha a jMenu en los metodos menuSelected, MenuDeselected y menuCanceled
        this.menu.jmSalir.addMenuListener(this);
    }

    public void iniciar() {
        menu.setTitle("Universidad de la Punta");
        menu.setLocationRelativeTo(null);
        menu.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.jmiFormularioAlumno) {
            AlumnoData data = new AlumnoData();
            VistaAlumnos vista = new VistaAlumnos();
            ControladorAlumnos a = new ControladorAlumnos(vista, data, menu);
            a.iniciar();
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.jmSalir) {
            menu.dispose();
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Métodos requeridos por COMPONENTLISTENER
    @Override
    public void componentResized(java.awt.event.ComponentEvent e) {

    }

    @Override
    public void componentMoved(java.awt.event.ComponentEvent e) {

    }

    @Override
    public void componentShown(java.awt.event.ComponentEvent e) {

    }

    @Override
    public void componentHidden(java.awt.event.ComponentEvent e) {

    }
}
