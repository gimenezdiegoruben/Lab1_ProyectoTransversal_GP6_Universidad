package ControladoresDeVistas;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import Persistencias_Conexion.AlumnoData;
import Persistencias_Conexion.MateriaData;
import Vistas.VistaAlumnos;
import Vistas.VistaMateria;
import Vistas.App_Menu;
import Vistas.VistaInscripcion;
import Persistencias_Conexion.InscripcionData;
import Vistas.VistaCargaNotas;
import Vistas.VistaListarInscripciones;

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
        this.menu.jmiFormularioMaterias.addActionListener(this);
        this.menu.jmiManejoInscripciones.addActionListener(this);
        this.menu.jmiManipulacionNotas.addActionListener(this);
        this.menu.jmiListarIncripciones.addActionListener(this);

        // AddMenuListener escucha a jMenu en los metodos menuSelected, MenuDeselected y menuCanceled
        this.menu.jmSalir.addMenuListener(this);
        this.menu.jFondo.addComponentListener(this);
    }

    public void iniciar() {
        menu.setTitle("Universidad de la Punta");
        menu.setLocationRelativeTo(null);
        menu.setResizable(false);
        ponerFondo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.jmiFormularioAlumno) {
            AlumnoData data = new AlumnoData();
            VistaAlumnos vista = new VistaAlumnos();
            ControladorAlumnos a = new ControladorAlumnos(vista, data, menu);
            a.iniciar();
        }
        if (e.getSource() == menu.jmiFormularioMaterias) {
            MateriaData data = new MateriaData();
            VistaMateria vista = new VistaMateria();
            ControladorMateria a = new ControladorMateria(vista, data, menu);
            a.iniciar();
        }
        if (e.getSource() == menu.jmiManejoInscripciones) {
            AlumnoData adata = new AlumnoData();
            InscripcionData idata = new InscripcionData();
            VistaInscripcion vista = new VistaInscripcion();
            ControladorInscripciones a = new ControladorInscripciones(vista, adata, idata, menu);
            a.iniciar();
        }
        if (e.getSource() == menu.jmiManipulacionNotas) {
            AlumnoData adata = new AlumnoData();
            InscripcionData idata = new InscripcionData();
            VistaCargaNotas vista = new VistaCargaNotas();
            ControladorCargaNotas a = new ControladorCargaNotas(adata, idata, vista, menu);
            a.inicia();
        }
        if (e.getSource() == menu.jmiListarIncripciones) {
            MateriaData data = new MateriaData();
            InscripcionData data1 = new InscripcionData();
            VistaListarInscripciones vista = new VistaListarInscripciones();
            ControladorListarInscripciones a = new ControladorListarInscripciones(data, data1, vista, menu);
            a.inicia();
        }
    }

    public void ponerFondo() {
        ClassLoader directorio = getClass().getClassLoader();
        URL rutaImagenFondo = directorio.getResource("Images/ULP.png");

        // Crea un ImageIcon a partir de la imagen de fondo
        ImageIcon imagenFondoIcon = new ImageIcon(rutaImagenFondo);

        // Obtiene la imagen de fondo
        Image imagenFondo = imagenFondoIcon.getImage();

        // Redimensiona la imagen de fondo al tamaño del JPanel
        imagenFondo = imagenFondo.getScaledInstance(menu.jFondo.getWidth(), menu.jFondo.getHeight(), Image.SCALE_SMOOTH);

        // Crea un nuevo ImageIcon con la imagen redimensionada
        ImageIcon imagenFondoRedimensionadaIcon = new ImageIcon(imagenFondo);

        // Crea una etiqueta JLabel para mostrar la imagen de fondo en el JPanel
        JLabel imagenFondoLabel = new JLabel(imagenFondoRedimensionadaIcon);

        // Establece la ubicación y el tamaño de la imagen de fondo
        imagenFondoLabel.setBounds(0, 0, menu.jFondo.getWidth(), menu.jFondo.getHeight());

        // Agrega la imagen de fondo al JPanel
        menu.jFondo.add(imagenFondoLabel);

        // Asegúrate de que la imagen de fondo esté en la parte posterior para no ocultar otros componentes
        menu.jFondo.setComponentZOrder(imagenFondoLabel, 0);

        // Actualiza el JPanel para mostrar la imagen
        menu.jFondo.revalidate();
        menu.jFondo.repaint();
    }

    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == menu.jmSalir) { //sale cuando el menu salir es seleccionado
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
    public void componentResized(ComponentEvent e) {
        ponerFondo();
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
