package test.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Cajero {
    int id_cajero;
    
    //Definimos la dirección en la cuál se encuentra la base de datos
    String url = "jdbc:sqlite:D:\\THE DHS MOTHERSHIP\\ESCUELA\\ESCOM\\5to Semestre\\ExamenAyDS\\Test\\src\\db\\Examen.db";
    //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
    Connection connect;

    public Cajero(int id_cajero) {
        this.id_cajero = id_cajero;
    }

    public int getId_cajero() {
        return id_cajero;
    }

    public void setId_cajero(int id_cajero) {
        this.id_cajero = id_cajero;
    }
    
    public Boolean validarCuenta(String numCuen) throws ClassNotFoundException {
        // Variable para almacenar el resultado de la consulta SQL "en crudo"
        ResultSet result;
        // Variable para almacenar el resultado de la consulta
        String numCuenta="";

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase Cajero se ha conectado a la base de datos");
            }
            
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT numCuenta from Cuenta "
                                                                + "where numCuenta = '" +numCuen+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                numCuenta = String.valueOf(result.getInt("numCuenta"));
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El numero de tarjeta de credito es: " + numCuenta);
            
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
        if(numCuenta!=""){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Boolean validarNIP(String nip, String numCuen) throws ClassNotFoundException {
        // Variable para almacenar el resultado de la consulta SQL "en crudo"
        ResultSet result;
        // Variable para almacenar el resultado de la consulta
        String nipUsuario="";

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase Cajero se ha conectado a la base de datos");
            }
            
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT NIP from Cuenta "
                                                                + "where NIP = '" +nip+"' "
                                                                + "AND numCuenta = '" +numCuen+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                nipUsuario = String.valueOf(result.getInt("NIP"));
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El numero de tarjeta de credito es: " + nipUsuario);
            
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
        if(nipUsuario!=""){
            return true;
        }
        else{
            return false;
        }
    }
}
