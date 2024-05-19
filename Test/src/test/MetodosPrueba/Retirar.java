/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.MetodosPrueba;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jarug
 */
public class Retirar {
    
    //Las siguientes dos lineas ya estarán definidas dentro de la clase Cliente, no es necesario incluirlas 
    //Definimos la dirección en la cuál se encuentra la base de datos
    String url = "jdbc:sqlite:D:\\Documents\\Semestre 2024-2\\AnalisisYDiseñoDeSistemas\\Examen\\Test\\src\\db\\Examen.db";
    //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
    Connection connect;
    double current=35000.0;
    
    
    public Retirar(double monto) throws ClassNotFoundException {
        try {
            // Cargamos dinámicamente la clase del driver para SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Guardamos la sentencia SQL en una variable string
            String query = "UPDATE Cuenta SET saldo = ? WHERE idCuenta = ?";
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement statement = connect.prepareStatement(query);
            
            // Definimos los parámetros a usar dentro del query
            statement.setDouble(1, (current-monto));
            statement.setString(2, "8"); //El número 8 será reemplazado por idCuenta

            // Ejecución de la sentencia UPDATE
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Saldo actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el saldo.");
            }

            // Cierre del objeto Statement
            statement.close();
           
        } catch (Exception e) {
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }
}
