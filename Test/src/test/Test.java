/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


package test;

import java.sql.*;
import test.clases.TarjetaCredito;
import test.MetodosPrueba.Retirar;

/**
 *
 * @author jarug
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
    
        
        
//        Class.forName("org.sqlite.JDBC");
//
//         
//        String url = "jdbc:sqlite:D:\\Documents\\Semestre 2024-2\\AnalisisYDiseñoDeSistemas\\Examen\\Test\\src\\db\\Examen.db";
//
//        try {
//            // Create a connection to the database
//            Connection conn = DriverManager.getConnection(url);
//
//            if (conn != null) {
//                DatabaseMetaData metaData = conn.getMetaData();
//                ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
//
//                // Iterate through the result set and print table names
//                while (resultSet.next()) {
//                    System.out.println(resultSet.getString("TABLE_NAME"));
//                }
//
//                // Close the result set and connection
//                resultSet.close();
//                conn.close();
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        //}
        
        //TarjetaCredito tarCre = new TarjetaCredito("5");
        //tarCre.actualizarDeuda(30000.0);
        
        Retirar ret = new Retirar(8500.0);
        
    }
    
}
