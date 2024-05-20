package test.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CuentaDebito {
    String numCuenta;
    //Cliente cliente; el Cliente será el que creé la instancia CuentaDebito
    String nip;
    double saldo;
    TarjetaCredito tarjetaCred;
    
    //Definimos la dirección en la cuál se encuentra la base de datos
    String url = "jdbc:sqlite:D:\\Documents\\Semestre 2024-2\\AnalisisYDiseñoDeSistemas\\Examen\\Test\\src\\db\\Examen.db";
    //Creamos un objeto de conexión para más tarde establecer comunicación con la base de datos
    Connection connect;
    
    
    
    // Constructor que solo recibe el numero de cuenta
    public CuentaDebito(String numCuenta) throws ClassNotFoundException {
        this.numCuenta = numCuenta;
        this.nip = obtenerNip(this.numCuenta);
        
        tarjetaCred = new TarjetaCredito(this.numCuenta);
        this.saldo = getSaldo(this.numCuenta);
    }
    
    
    private String obtenerNip(String numCuenta) throws ClassNotFoundException {
        ResultSet result = null;
        String nipCu="";

        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");

        try{
            // Establecemos conexión con la base de datos
            connect = DriverManager.getConnection(url);
            
            // Verificamos que la conexión sea exitosa
            if(connect != null){
             System.out.println("Clase cuentaDebito se ha conectado a la base de datos");
            }
        
            // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
            PreparedStatement st = connect.prepareStatement("SELECT NIP from Cuenta "
                                                                + "where idCuenta = '" +numCuenta+"'");
            // Obtención de respuesta de SQL
            result = st.executeQuery();
            while(result.next()){
                nipCu=result.getString("NIP");
            }
            
            // Imprimimos la fecha de expiración de la tarjeta
            System.out.println("El NIP de la cuenta es: " + nipCu);
            
        } catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       
       return nipCu;
    }
    
    
    
    private double getSaldo(String idCuenta) throws ClassNotFoundException {
    ResultSet resultado = null;
    double saldoTot = 0;

    try {
        // Cargamos dinámicamente la clase del driver para SQLite
        Class.forName("org.sqlite.JDBC");
        
        // Establecemos conexión con la base de datos
        connect = DriverManager.getConnection(url);
        
        // Verificamos que la conexión sea exitosa
        if (connect != null) {
            System.out.println("Clase CuentaDebito se ha conectado a la base de datos");
        }
        
        // Consulta para la base de datos
        String sql = "SELECT saldo FROM Cuenta WHERE idCuenta = ?";
        
        // Creación de un objeto PreparedStatement que representa una sentencia SQL precompilada
        PreparedStatement st = connect.prepareStatement(sql);
        st.setString(1, idCuenta);
       
        // Obtención de respuesta de SQL
        resultado = st.executeQuery();
        while (resultado.next()) {
            saldoTot = resultado.getDouble("saldo");
        }
        
        // Imprimimos el crédito total de la cuenta
        System.out.println("El saldo total de la tarjeta es: " + String.valueOf(saldoTot));
        
    } catch (Exception x) {
        JOptionPane.showMessageDialog(null, x.getMessage().toString());
    }
    return saldoTot;
}

    

     
    
    
    /*
    public static void main(String[] args) throws ClassNotFoundException {
        // Crear una nueva transacción
        CuentaDebito cuentDeb = new CuentaDebito("3");
        // Mostrar el estatus de la transacción
        System.out.println("Nip: " + cuentDeb.obtenerNip(cuentDeb.numCuenta));
    }
    */
    
}
