package test.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Clase que representa una transacción.
 */

/*
    private idTransaccion
    ---constructor-----
    crear una nueva transaccion(tipo, cantidad)             
        conectarse a la base de datos
        guardas la transaccion y te va crear un idTransaccion y lo guardas en el atributo privado de la clase

    ----metodo actualizar saldo----
    actualizarSaldo(CuentaDebito cuenta)
        cuenta.setSaldo(this.cantidad) (este metodo viene de cuentaDebito)
        guardas el idTransaccion y el idCuenta en la tabla q los relaciona
        
*/

public class Transaccion {
    // Atributos
    private int idTransaccion;
    private int cantidad;
    private Date fecha;
    private int tipo;
    private boolean estatus;

    // Constructor
    public Transaccion(int monto, int tipo) throws ClassNotFoundException {
        this.cantidad = monto;
        this.fecha = new Date();
        this.tipo = tipo;
        
        String currentDir = System.getProperty("user.dir");    
        String url = "jdbc:sqlite:"+currentDir+"/src/db/Examen.db";
        //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
        Connection connect;
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
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Transaccion (monto, fechaHora, tipo) VALUES (?, ?, ?)");
            
            statement.setDouble(1, monto);
            statement.setString(2, String.valueOf(this.fecha));
            statement.setString(3, String.valueOf(tipo));
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Monto actualizada correctamente.");
                
                PreparedStatement st = connect.prepareStatement("SELECT idTransaccion from TRANSACCION ORDER BY idTransaccion DESC LIMIT 1");
                result = st.executeQuery();
                while(result.next()){
                    this.idTransaccion = result.getInt("numCuenta");
                    System.out.println(this.idTransaccion);
                }
            } else {
                System.out.println("No se pudo actualizar el monto.");
            }
            
            statement.close();
        } catch(Exception x){
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
    }

    // Método para actualizar el saldo en una cuenta de débito
    private void actualizarSaldo(CuentaDebito cuenta) {
        cuenta.setSaldo(this.cantidad);
        
        String currentDir = System.getProperty("user.dir");    
        String url = "jdbc:sqlite:"+currentDir+"/src/db/Examen.db";
        //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
        Connection connect;
        try {
            // Cargamos dinámicamente la clase del driver para SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Guardamos la sentencia SQL en una variable string
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Cuenta_Transaccion (idCuenta_CuTra, idTransaccion_CuTra) VALUES (?, ?)");
            
            statement.setDouble(1, this.idTransaccion);
            statement.setString(2, );
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Monto actualizada correctamente.");
            } else {
                System.out.println("No se pudo actualizar el monto.");
            }
            
            statement.close();
           
        } catch (Exception e) {
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    // Método para obtener el estatus de la transacción
    private boolean obtenerEstatus(int cantidad, Date fecha) {
        // Aquí se implementaría la lógica para determinar el estatus de la transacción.
        // Por ahora, siempre devuelve true.
        return true;
    }

    // Método para mostrar el estatus de la transacción
    private boolean mostrarStatus() {
        return this.estatus;
    }

    // Getters y Setters
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    // Método principal para probar la clase
    public static void main(String[] args) {
        // Crear una nueva transacción
        //Transaccion transaccion = new Transaccion(100, new Date());
        // Mostrar el estatus de la transacción
        //System.out.println("Estatus de la transacción: " + transaccion.mostrarStatus());
    }
}
