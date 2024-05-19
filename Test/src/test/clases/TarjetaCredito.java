/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    String numCuenta;
    String numTarjeta;
    String expiracion;
    double creditoTotal;
    double deuda;
    
    String url = "jdbc:sqlite:D:\\Documents\\Semestre 2024-2\\AnalisisYDiseñoDeSistemas\\Examen\\Test\\src\\db\\Examen.db";
    Connection connect;
    
    
    
    
    public TarjetaCredito(String cuenta) throws ClassNotFoundException{
        //Métodos encapsulados para inicialización de atributos:
        numCuenta =cuenta;
        numTarjeta = obtenerTarjeta(numCuenta);
        expiracion = obtenerExpiracion(numTarjeta);
        creditoTotal = obtenerCreditoTotal(numTarjeta);
        deuda = obtenerDeuda(numTarjeta);
        
        

    }
    
    
    

    
    public void actualizarDeuda(double monto) throws ClassNotFoundException{
        //throw new UnsupportedOperationException("Not supported yet.");        
        Class.forName("org.sqlite.JDBC");
              
       try{
            connect = DriverManager.getConnection(url);
            
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            PreparedStatement st = connect.prepareStatement("UPDATE TarjetaCredito SET deuda = " + monto
                                                                + "WHERE idTarjeta = '" +numTarjeta+"'");
            st.executeQuery();
            
            System.out.println("La deuda en la tarjeta es: " + String.valueOf(deuda));
            
            double comp = obtenerDeuda(numTarjeta);
            
        }catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
    }

    private String obtenerTarjeta(String numCu) throws ClassNotFoundException {
        ResultSet result = null;
        String numTar="";
        
        Class.forName("org.sqlite.JDBC");
              
       try{
            connect = DriverManager.getConnection(url);
            
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            PreparedStatement st = connect.prepareStatement("SELECT idTarjetaCredito_CuTar from Cuenta_TarjetaCredito "
                                                                + "where idCuenta_CuTar = '" +numCu+"'");
            result = st.executeQuery();
            while(result.next()){
                numTar=String.valueOf(result.getInt("idTarjetaCredito_CuTar"));
            }
            
            
            System.out.println("El numero de tarjeta de credito es: " + numTar);
            
        }catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
           return numTar;
    }

    
    
    private String obtenerExpiracion(String numTar) throws ClassNotFoundException {
        ResultSet result = null;
        String expir="";
        
        Class.forName("org.sqlite.JDBC");
              
       try{
            connect = DriverManager.getConnection(url);
            
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            PreparedStatement st = connect.prepareStatement("SELECT expira from TarjetaCredito "
                                                                + "where idTarjeta = '" +numTar+"'");
            result = st.executeQuery();
            while(result.next()){
                expir=result.getString("expira");
            }
            
            
            System.out.println("La expiración de la tarjeta es: " + expir);
            
        }catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       
       return expir;
    }

    private double obtenerCreditoTotal(String numTar) throws ClassNotFoundException {
        //throw new UnsupportedOperationException("Not supported yet."); 
        ResultSet result = null;
        double creditoTot=0;
        
        Class.forName("org.sqlite.JDBC");
              
       try{
            connect = DriverManager.getConnection(url);
            
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            PreparedStatement st = connect.prepareStatement("SELECT credito from TarjetaCredito "
                                                                + "where idTarjeta = '" +numTar+"'");
            result = st.executeQuery();
            while(result.next()){
                creditoTot=result.getDouble("credito");
            }
            
            
            System.out.println("El credito total de la tarjeta es: " + String.valueOf(creditoTot));
            
        }catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       return creditoTot;
    }

    private double obtenerDeuda(String numTar) throws ClassNotFoundException {
        //throw new UnsupportedOperationException("Not supported yet."); 
        ResultSet result = null;
        double deuda=0;
        
        Class.forName("org.sqlite.JDBC");
              
       try{
            connect = DriverManager.getConnection(url);
            
            if(connect != null){
             System.out.println("Clase TarjetaCredito se ha conectado a la base de datos");
            }
        
            PreparedStatement st = connect.prepareStatement("SELECT deuda from TarjetaCredito "
                                                                + "where idTarjeta = '" +numTar+"'");
            result = st.executeQuery();
            while(result.next()){
                deuda=result.getDouble("deuda");
            }
            
            
            System.out.println("La deuda en la tarjeta es: " + String.valueOf(deuda));
            
        }catch(Exception x){
            JOptionPane.showMessageDialog(null, x.getMessage().toString());
        }
       return deuda;
    }

  
    
}
