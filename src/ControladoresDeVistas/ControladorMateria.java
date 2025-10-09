package ControladoresDeVistas;

import Persistencias_Conexion.MateriaData;
import Vistas.App_Menu;
import Vistas.VistaMateria;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

    public ControladorMateria(VistaMateria vista, MateriaData data, App_Menu menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;

        // Se Colocan los objetos que tendran ActionListener
        // Se declaran los objetos que tendran FocusListener
        // Se declaran los objetos que tendran KeyListener
    }

    public void iniciar() {

        //menu.jFondo.removeAll();
        //menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus(); //le da el foco al formulario
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e){
        // aquí todos los actionPerformed
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
