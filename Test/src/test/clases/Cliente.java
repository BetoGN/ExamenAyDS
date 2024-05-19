/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.clases;

import java.util.Scanner;

/**
 *
 * @author yeimicarmona
 */
public class Cliente {
      private String nombre;
      private String numeroTarjeta;
      private String nip;
      private CuentaDebito tarjetaDebito;
      private Scanner scanner;
      
      
      public void insertarTarjeta(String numeroTarjeta){
          this.numeroTarjeta = numeroTarjeta;
          System.out.printf("soy de la clase cliente %s", this.numeroTarjeta);
      }
      
      public void insertarNip(String nip){
          this.nip = nip;
          System.out.printf("soy de la clase cliente %s", this.nip);
      }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getNip() {
        return nip;
    }

    @Override
    public String toString() {
        return "Cliente{" + "numeroTarjeta=" + numeroTarjeta + '}';
    }
    
    
      
}

