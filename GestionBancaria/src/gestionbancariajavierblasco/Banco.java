/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbancariajavierblasco;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author J.Blasco
 */
public class Banco {

    //ATRIBUTOS
    private String nombreBanco, direccionBanco;
    long numeroCuenta = 1000;
    Map<Long, CuentaBancaria> CuentasClientes = new HashMap<>();

    //CONTRUCTOR
    public Banco(String nombreBanco, String direccionBanco) {
        this.nombreBanco = nombreBanco;
        this.direccionBanco = direccionBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getDireccionBanco() {
        return direccionBanco;
    }

    public void setDireccionBanco(String direccionBanco) {
        this.direccionBanco = direccionBanco;
    }

    public CuentaBancaria crearCuenta(String nif, String nombre) {

        Persona titular = new Persona(nif, nombre);
        CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta++, titular);

        CuentasClientes.put(cuenta.getNumCuenta(), cuenta);

        return cuenta;
    }

    public int eliminarCuenta(long numCuenta) {
        
        if (CuentasClientes.containsKey(numCuenta)) {
           
            CuentaBancaria cEncontrada = CuentasClientes.get(numCuenta);
               
                    if (cEncontrada.getSaldo() > 0) {
                        //double saldo2 = cEncontrada.getSaldo();
                        //System.out.println(saldo2);
                        return -2;
                    }
                    if (cEncontrada.getSaldo() == 0) {
                        CuentasClientes.remove(numCuenta);
                        return 0;
                    }
              

        }
        return -1;
    }

    public Set<CuentaBancaria> listarCuentas(String nif) {
        Set<CuentaBancaria> listadoCuentas = new HashSet<>();
        for (Entry<Long, CuentaBancaria> itCuentas : CuentasClientes.entrySet()) {
            if (itCuentas.getValue().getTitular().getNif().equalsIgnoreCase(nif)) {
                listadoCuentas.add(itCuentas.getValue());
            }
        }

        return listadoCuentas;
    }

    public CuentaBancaria localizarCC(long ncuenta) {
        if (CuentasClientes.containsKey(ncuenta)) {
            for (long ncuentaiterator : CuentasClientes.keySet()) {
                if (ncuenta == ncuentaiterator) {
                    CuentaBancaria cuentaEncontrada = CuentasClientes.get(ncuentaiterator);
                    return cuentaEncontrada;
                }
            }
        }
        return null;
    }

    public boolean existeCuenta(long ncuenta) {
        boolean respuesta = false;
        if (CuentasClientes.containsKey(ncuenta)) {
            respuesta = true;
        }
        return respuesta;
    }

    
    public void datosInicio(){
        
    crearCuenta("111","Paco");
    crearCuenta("111","Paco");
    crearCuenta("222","Miguel");
    crearCuenta("333","Juan");
    crearCuenta("444","Marta");
    crearCuenta("555","Alba");
    crearCuenta("666","José");
    
    }
    
}
