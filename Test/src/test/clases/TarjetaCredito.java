
package test.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author jarug
 */
public class TarjetaCredito {
    //Atributos pertenecientes a la clase
    private String numCuenta;
    private String numTarjeta;
    private String expiracion;
    private double creditoTotal;
    private double deuda;
    
    //Definimos la dirección en la cuál se encuentra la base de datos
    String url = "jdbc:sqlite:D:\\Documents\\Semestre 2024-2\\AnalisisYDiseñoDeSistemas\\Examen\\Test\\src\\db\\Examen.db";
    //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
    Connection connect;
    
    
    
    
       // Constructor de la clase TarjetaCredito
    public TarjetaCredito(String cuenta) throws ClassNotFoundException{
        // Métodos encapsulados para inicialización de atributos:
        this.numCuenta = cuenta;
        this.numTarjeta = obtenerTarjeta(numCuenta);
        this.expiracion = obtenerExpiracion(numTarjeta);
        this.creditoTotal = obtenerCreditoTotal(numTarjeta);
        this.deuda = obtenerDeuda(numTarjeta);
    }

    // Método para actualizar la deuda en la tarjeta de crédito
    public void actualizarDeuda(double monto) throws ClassNotFoundException {
        try {
            // Cargamos dinámicamente la clase del driver para SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Guardamos la sentencia SQL en una variable string
            String query = "UPDATE TarjetaCredito SET deuda = ? WHERE idTarjeta = ?";
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement statement = connect.prepareStatement(query);
            
            // Definimos los parámetros a usar dentro del query
            statement.setDouble(1, monto);
            statement.setString(2, numTarjeta);

            // Ejecución de la sentencia UPDATE
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deuda actualizada correctamente.");
            } else {
                System.out.println("No se pudo actualizar la deuda.");
            }

            // Cierre del objeto Statement
            statement.close();
           
        } catch (Exception e) {
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    // Método para obtener el número de tarjeta de crédito asociado a una cuenta
    private String obtenerTarjeta(String numCu) throws ClassNotFoundException {
        // Variable para almacenar el resultado de la consulta SQL "en crudo"
        ResultSet result;
        // Variable para almacenar el resultado de la consulta
        String numTar="";

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
            
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT idTarjetaCredito_CuTar from Cuenta_TarjetaCredito "
                                                                + "where idCuenta_CuTar = '" +numCu+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                numTar=String.valueOf(result.getInt("idTarjetaCredito_CuTar"));
            }
            
            // Imprimimos el número de tarjeta de crédito
            System.out.println("El numero de tarjeta de credito es: " + numTar);
            
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
           return numTar;
    }

    // Método para obtener la fecha de expiración de la tarjeta de crédito
    private String obtenerExpiracion(String numTar) throws ClassNotFoundException {
        ResultSet result = null;
        String expir="";

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT expira from TarjetaCredito "
                                                                + "where idTarjeta = '" +numTar+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                expir=result.getString("expira");
            }
            
            // Imprimimos la fecha de expiración de la tarjeta
            System.out.println("La expiración de la tarjeta es: " + expir);
            
        } catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       
       return expir;
    }

    // Método para obtener el crédito total asociado a una tarjeta de crédito
    private double obtenerCreditoTotal(String numTar) throws ClassNotFoundException {
        ResultSet result = null;
        double creditoTot=0;

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT credito from TarjetaCredito "
                                                                + "where idTarjeta = '" +numTar+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                creditoTot=result.getDouble("credito");
            }
            
            // Imprimimos el crédito total de la tarjeta
            System.out.println("El credito total de la tarjeta es: " + String.valueOf(creditoTot));
            
        } catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       return creditoTot;
    }

    // Método para obtener la deuda asociada a una tarjeta de crédito
    private double obtenerDeuda(String numTar) throws ClassNotFoundException {
        ResultSet result = null;
        double deuda=0;

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT deuda from TarjetaCredito "
                                                                + "where idTarjeta = '" +numTar+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                deuda=result.getDouble("deuda");
            }
            
            // Imprimimos la deuda de la tarjeta
            System.out.println("La deuda en la tarjeta es: " + String.valueOf(deuda));
            
        } catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       return deuda;
    }
}
