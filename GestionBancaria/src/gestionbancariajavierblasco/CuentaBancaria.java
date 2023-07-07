/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbancariajavierblasco;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author J.Blasco
 */
public class CuentaBancaria {

    //formato decimal de la cuenta
    static DecimalFormat formatea = new DecimalFormat("###,###.##");//crearlo en los metodos donde lo utilizamos

    private long numCuenta;
    private Persona titular;
    private double saldo;
    private Set<Persona> autorizados = new HashSet<>();
    //private Set<Long> cuentaEncontrada = new HashSet<>();
    private final int MIN_REGALO = 0, MAX_REGALO = 50;//al crear la cuenta ingresa de regalo un aleatorio de 1 a 50 sin decimales
    //cambiar el atributo de sitio, no en cuenta bancaria con un metodo
 

    public CuentaBancaria(long ncuenta, Persona titular) {//constructor
        this.numCuenta = ncuenta;
        this.titular = titular;
        this.saldo = (int) (Math.random() * (MAX_REGALO - MIN_REGALO + 1) + MIN_REGALO);
    }

    //NO HAY METODOS SET PORQUE NO SE DEBE PERMITIR MODIFICAR DATOS DESPUES DE 
    // CREAR EL OBJETO
    public long getNumCuenta() {
        return numCuenta;
    }

    public Persona getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public double ingresar(double cantidad) {
        if (cantidad >= 0) {
            saldo = saldo + cantidad;
        }
        return saldo;
    }

    public double sacar(double cantidad) {
        if (cantidad <= saldo && cantidad >= 0) {
            saldo = saldo - cantidad;
        }
        return saldo;
    }

    public String informacionCuenta() {
        String resultado = "nº de cuenta : " + numCuenta + " - " + titular.getNombre() + " \n";
        if (!this.autorizados.isEmpty()) {
            resultado += "Personas autorizadas: " + autorizados + "\n";
        }
        resultado += "saldo: " + formatea.format(saldo) + " €";
        return resultado;
    }

    public boolean autorizar(String dni, String nombre) {
        Persona autorizado = new Persona(dni, nombre);
        boolean resultado = false;//por defecto no incluimos el nuevo

        if (autorizados.isEmpty()) {//Si no hay autorizado, lo incluimos
            autorizados.add(autorizado);
            resultado = true;
        } else if (this.existe(dni) == null) {//si existe algun autorizado pero no coincide con el nuevo lo incluimos
            autorizados.add(autorizado);
            resultado = true;
        }
        return resultado;
    }

    public boolean desautorizar(String dni) {
        boolean desautorizado = false;
        Persona per = existe(dni);
        if (per != null) {
            autorizados.remove(per);
            desautorizado = true;
        }
        return desautorizado;
    }

    public String verAutorizados() {
       
        return "Personas autorizadas: " + autorizados;

    }

    public Persona existe(String dni) {
        Persona perso = null;
        Persona encontrado = null;
        Iterator<Persona> it = this.autorizados.iterator();

        while (it.hasNext() && encontrado == null) {
            perso = it.next();
            if (perso.igual(dni)) {
                encontrado = perso;
            }
        }
        return encontrado;
    }
    
    
     

}
