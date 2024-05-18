/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


package test;

import java.sql.*;

/**
 *
 * @author jarug
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        Connection c = null;

        try {

            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("dbc:sqlite:db\\Examen.db");

        }catch ( Exception e ) {

            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            System.exit(0);

        }

    //System.out.println("database successfully created");
        
    }
    
}
