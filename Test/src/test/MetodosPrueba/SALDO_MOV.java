package test.MetodosPrueba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

public class SALDO_MOV {
    String numTarjeta;
    String numCuenta;
    String url = "jdbc:sqlite:D:/Documents/Semestre 2024-2/AnalisisYDiseñoDeSistemas/Examen/Test/src/db/Examen.db";
    Connection connect;

    // Método para consultar movimientos
    public void consultarMovimientos() throws ClassNotFoundException {
        ResultSet result = null;

        try {
            Class.forName("org.sqlite.JDBC");

            connect = DriverManager.getConnection(url);

            if (connect != null) {
                System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }

            String query = "SELECT * FROM Movimientos WHERE idTarjeta = ?";
            PreparedStatement st = connect.prepareStatement(query);
            st.setString(1, numTarjeta);
            result = st.executeQuery();
            
            while (result.next()) {
                int cantidad = result.getInt("cantidad");
                Date fecha = result.getDate("fecha");
                boolean estatus = result.getBoolean("estatus");
                System.out.println("Movimiento: Cantidad=" + cantidad + ", Fecha=" + fecha + ", Estatus=" + (estatus ? "Exitoso" : "Fallido"));
            }

            result.close();
            st.close();
            connect.close();
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
    }

    // Método para consultar el balance
    public double consultarBalance() throws ClassNotFoundException {
        double balance = 0;

        try {
            Class.forName("org.sqlite.JDBC");

            connect = DriverManager.getConnection(url);

            if (connect != null) {
                System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }

            String query = "SELECT saldo FROM CuentaDebito WHERE idCuenta = ?";
            PreparedStatement st = connect.prepareStatement(query);
            st.setString(1, numCuenta);
            ResultSet result = st.executeQuery();
            
            if (result.next()) {
                balance = result.getDouble("saldo");
                System.out.println("El saldo de la cuenta es: " + balance);
            }

            result.close();
            st.close();
            connect.close();
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }

        return balance;
    }
}
