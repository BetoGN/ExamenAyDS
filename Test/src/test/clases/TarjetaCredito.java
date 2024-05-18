/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.clases;

/**
 *
 * @author jarug
 */
public class TarjetaCredito {
    String numTarjeta;
    String expiracion;
    double creditoTotal;
    double deuda;
    
    TarjetaCredito(String numCuenta){
        //Métodos encapsulados para inicialización de atributos:
        numTarjeta = obtenerTarjeta(numCuenta);
        expiracion = obtenerExpiracion(numCuenta);
        creditoTotal = obtenerCreditoTotal(numCuenta);
        deuda = obtenerDeuda(numCuenta);
    }
    
    private void actualizarDeuda(double monto){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String obtenerTarjeta(String numCuenta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String obtenerExpiracion(String numCuenta) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private double obtenerCreditoTotal(String numCuenta) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private double obtenerDeuda(String numCuenta) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
