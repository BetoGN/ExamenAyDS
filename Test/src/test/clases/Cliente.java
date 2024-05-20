/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yeimicarmona
 */
public class Cliente {
      private String idCliente;

    
      private String nombre;
      private String apellidoMaterno;
      private String apellidoPaterno;
    
      
      public String getIdCliente() {
        return idCliente;
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
      
      public Cliente(String idCliente){
          try {
              this.idCliente = idCliente;
              ResultSet result;
              // Print the current working directory
              String currentDir = System.getProperty("user.dir");    
              String url = "jdbc:sqlite:"+currentDir+"/src/db/Examen.db";
              Connection connect;
              Class.forName("org.sqlite.JDBC");
              connect = DriverManager.getConnection(url);
              // Verificamos que la conexión sea exitosa
              if(connect != null){
                System.out.println("Clase Cliente se ha conectado a la base de datos");
              }
            
              // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st1 = connect.prepareStatement("SELECT nombre from Cliente "
                                                                + "where idCliente = '" +idCliente+"'");
            
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st2 = connect.prepareStatement("SELECT apPaterno from Cliente "
                                                                + "where idCliente = '" +idCliente+"'");
            
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st3 = connect.prepareStatement("SELECT apMaterno from Cliente "
                                                                + "where idCliente = '" +idCliente+"'");
            // Obtención de respuesta de SQL
            result = st1.executeQuery();
            while(result.next()){
                this.nombre = result.getString("nombre");
            }
            result = st2.executeQuery();
            while(result.next()){
                this.apellidoPaterno = result.getString("apPaterno");
            }
            result = st3.executeQuery();
            while(result.next()){
                this.apellidoMaterno = result.getString("apMaterno");
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El nombre es: " + this.nombre);
            System.out.println("El ape Paterno es: " + this.apellidoPaterno);
            System.out.println("El ape Materno es: " + this.apellidoMaterno);
              
              
          } catch (ClassNotFoundException ex) {
              System.out.println("Error");
          } catch (SQLException ex) {
              Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
          }
          
      }

      public static void main(String args[]) {
        new Cliente("1");
    }
}

