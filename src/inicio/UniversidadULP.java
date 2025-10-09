package inicio;

import ControladoresDeVistas.ControladorApp_Menu;
import Vistas.App_Menu;

/*  @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */

public class UniversidadULP {

    public static void main(String[] args) {
        App_Menu menu = new App_Menu();
        ControladorApp_Menu crlmenu = new ControladorApp_Menu(menu);
        crlmenu.iniciar();
        menu.setVisible(true);
    }
}
