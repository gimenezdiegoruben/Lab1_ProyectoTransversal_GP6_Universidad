/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Vistas;

import Persistencias_Conexion.Conexion;
import java.sql.Connection;
/**
 *
 * @author Ger
 */
public class Tests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Test para conectarse a la base de datos
        Connection con= Conexion.getConexion();
        
    }
    
}
