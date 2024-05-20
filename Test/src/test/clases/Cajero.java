package test.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Cajero {
    int id_cajero;
    
    String currentDir = System.getProperty("user.dir");    
    String url = "jdbc:sqlite:"+currentDir+"/src/db/Examen.db";
    //Definimos la dirección en la cuál se encuentra la base de datos
    //String url = "jdbc:sqlite:D:\\THE DHS MOTHERSHIP\\ESCUELA\\ESCOM\\5to Semestre\\ExamenAyDS\\Test\\src\\db\\Examen.db";
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
    
    public Integer depositarACuenta(String numCuen, int cantidad) throws ClassNotFoundException {
        // Variable para almacenar el resultado de la consulta SQL "en crudo"
        ResultSet result;
        // Variable para almacenar el resultado de la consulta
        int cantidadActual = 0;

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
            PreparedStatement st = connect.prepareStatement("SELECT saldo from Cuenta "
                                                                + "where numCuenta = '" +numCuen+"' ");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                cantidadActual = result.getInt("saldo") + cantidad;
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El saldo actual es: " + cantidadActual);
            actualizarSaldo(cantidadActual, numCuen);
            
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
        
        return cantidadActual;
    }
    
    public Integer retirarEfectivo(String numCuen, int cantidad) throws ClassNotFoundException {
        // Variable para almacenar el resultado de la consulta SQL "en crudo"
        ResultSet result;
        // Variable para almacenar el resultado de la consulta
        int cantidadActual = 0;

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
            PreparedStatement st = connect.prepareStatement("SELECT saldo from Cuenta "
                                                                + "where numCuenta = '" +numCuen+"' ");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                cantidadActual = result.getInt("saldo") - cantidad;
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El saldo actual es: " + cantidadActual);
            actualizarSaldo(cantidadActual, numCuen);
            
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
        
        return cantidadActual;
    }
    
    public void actualizarSaldo(int monto, String numCuenta) throws ClassNotFoundException {
        try {
            // Cargamos dinámicamente la clase del driver para SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Guardamos la sentencia SQL en una variable string
            String query = "UPDATE Cuenta SET saldo = ? WHERE numCuenta = ?";
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement statement = connect.prepareStatement(query);
            
            // Definimos los parámetros a usar dentro del query
            statement.setDouble(1, monto);
            statement.setString(2, numCuenta);

            // Ejecución de la sentencia UPDATE
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Monto actualizada correctamente.");
            } else {
                System.out.println("No se pudo actualizar el monto.");
            }

            // Cierre del objeto Statement
            statement.close();
           
        } catch (Exception e) {
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }
    
    public Integer consultarSaldo(String numCuen) throws ClassNotFoundException {
        // Variable para almacenar el resultado de la consulta SQL "en crudo"
        ResultSet result;
        // Variable para almacenar el resultado de la consulta
        int saldo = 0;

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
            PreparedStatement st = connect.prepareStatement("SELECT saldo from Cuenta "
                                                                + "where numCuenta = '" +numCuen+"' ");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                saldo = result.getInt("saldo");
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El saldo actual es: " + saldo);
            
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
        
        return saldo;
    }
}
