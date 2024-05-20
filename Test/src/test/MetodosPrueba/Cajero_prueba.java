package test.MetodosPrueba;

import test.clases.Cajero;
import test.clases.Transaccion;

public class Cajero_prueba {
    public static void main(String[] args) throws ClassNotFoundException {
        Cajero cajero1 = new Cajero(0);
        
        Boolean validarCuenta = cajero1.validarCuenta("18291");
        System.out.println(validarCuenta);
        
        Boolean validarNIP = cajero1.validarNIP("3141", "18291");
        System.out.println(validarNIP);
        
        Integer saldoDepositado = cajero1.depositarACuenta("18291", 1000);
        System.out.println(saldoDepositado);
        
        
        
        Integer saldo = cajero1.consultarSaldo("18291");
        System.out.println(saldo);
        
        //cajero1.obtenerDatosCliente(1);
        
    }
}
