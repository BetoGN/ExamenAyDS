package test.clases;

import java.util.Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Clase que representa una transacción.
 */
public class Transaccion {
    // Atributos
    private double cantidad;
    private Calendar fechaHora;
    private boolean estatus;
    private boolean tipo;
    
    //Definimos la dirección en la cuál se encuentra la base de datos
    String url = "jdbc:sqlite:C:\\Users\\abrah\\Documents\\0_Examen_ADS\\ExamenAyDS\\Test\\src\\db\\Examen.db";
    
    //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
    Connection connect;
    
    
    // Constructor
    public Transaccion(double cantidad, boolean tipo) {
        // Se obtiene la fecha y hora actual 
        Calendar fecha = Calendar.getInstance();
        
        this.cantidad = cantidad;
        this.fechaHora = fecha;
        this.estatus = false;
        this.tipo = tipo;
    }
    
    // Metodo para hacer el registro en la base de datos
    public void insertarTransaccion() {
        // Se prepara el dato de fecha para insertar en la BD
        Calendar fecha = this.fechaHora;
        int hora, minuto, segundo, mes, ano, dia;
    
        hora = fecha.get(Calendar.HOUR_OF_DAY);
        minuto = fecha.get(Calendar.MINUTE);
        segundo = fecha.get(Calendar.SECOND); 

        ano = fecha.get(Calendar.YEAR); 
        mes = fecha.get(Calendar.MONTH) + 1; 
        dia = fecha.get(Calendar.DATE);
        
        // Se formatea la fehca y hora para su almacenamiento en BD
        String fechaHora = String.format("%04d-%02d-%02d %02d:%02d:%02d", 
                                         ano, mes, dia, hora, minuto, segundo);
        
        try {
            // Cargamos dinámicamente la clase del driver para SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
                System.out.println("Clase Transaccion se ha conectado a la base de datos");
            }
            
            // Consulta de inserción a la BD
            String sql = "INSERT INTO Transaccion(monto, fechaHora, tipo) VALUES (?, ?, ?)";
            PreparedStatement registro = connect.prepareStatement(sql);
            registro.setDouble(1, cantidad);
            registro.setString(2, fechaHora);
            registro.setInt(3, tipo ? 1 : 0); // Convertimos booleano a 1 o 0

            // Se ejecuta la inserción de los datos en la BD
            registro.executeUpdate();
            // Poner en true el estatus de la transacción
            this.estatus = true;
            System.out.println("Se inserto correctamente: " + registro);
            
        } catch (Exception e) {
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        } finally {
            // Desconectar de la BD
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
 
    // Método para actualizar el saldo en una cuenta de débito
    private void actualizarSaldo(CuentaDebito cuenta) {
        try {
            double saldoActual = 0.0;
            double saldoNuevo = 0.0;
            // Cargamos dinámicamente la clase del driver para SQLite
            Class.forName("org.sqlite.JDBC");
            
            ResultSet rs = null;
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
                System.out.println("Clase Transaccion se ha conectado a la base de datos");
            }
            
            // Consultar saldo actual de la cuenta
            String sql = "SELECT saldo FROM Cuenta WHERE numCuenta = ?";
            PreparedStatement registro = connect.prepareStatement(sql);
            registro.setString(1, cuenta.numCuenta);
            rs = registro.executeQuery();
            
            // Se guarda el saldo actual para operar con el 
            if(rs.next()) {
                saldoActual = rs.getDouble("saldo");
            }
            
            // El monto es suamdo a la cuenta si tipo = true, en caso contrario
            // se resta
            if(this.tipo) {
                saldoNuevo = saldoActual + this.cantidad;
            } else {
                saldoNuevo = saldoActual - this.cantidad;
            }
            
            // Se actualiza en la BD el saldo nuevo
            sql = "UPDATE Cuenta SET saldo = ? WHERE numCuenta = ?";
            registro = connect.prepareStatement(sql);
            registro.setDouble(1, saldoNuevo);
            registro.setString(2, cuenta.numCuenta);

            // Se ejecuta la actualización de los datos en la BD
            registro.executeUpdate();
            
        } catch (Exception e) {
            // Retornamos el error que pueda llegar a atrapar el compilador
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        } finally {
            // Desconectar de la BD
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }    

    // Método para mostrar el estatus de la transacción
    private boolean mostrarStatus() {
        return this.estatus;        
    }

    // Getters y Setters
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Calendar getFecha() {
        return fechaHora;
    }

    public void setFecha(Calendar fecha) {
        this.fechaHora = fecha;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    // Método principal para probar la clase
    public static void main(String[] args) throws ClassNotFoundException {
        // Crear una nueva transacción
        Transaccion transaccion = new Transaccion(110.0, true);
        // Mostrar el estatus de la transacción
        transaccion.insertarTransaccion();
        
        CuentaDebito cuenta = new CuentaDebito("68842");
        transaccion.actualizarSaldo(cuenta);
        System.out.println("\n\nsaldo: " + cuenta.getSaldo("68842") +"\n\n");
        System.out.println("Estatus de la transacción: " + transaccion.mostrarStatus());
    }
}
