
package com.mycompany.empresa;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class conexion {
    Connection conectar = null;
    String usuario = "root";
    String contrasenia = "";
    String bd = "bdempresa";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
    try{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
        //JOptionPane.showMessageDialog(null, "La conexion es todo un exito");
    
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, "El error es el siguiente: " +e.toString());
    
    }
    return conectar;
    }
    
    
}
