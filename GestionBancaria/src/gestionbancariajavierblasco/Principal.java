/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbancariajavierblasco;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author J.Blasco
 */
public class Principal {

    

    static Scanner sc = new Scanner(System.in);
    static CuentaBancaria cuenta;
    static Banco banco;
    static DecimalFormat formatea = new DecimalFormat("###,###.##");

    public static void main(String[] args) {
        Banco banco1 = new Banco("Banco de Valencia", "Plaza del ayuntamiento");

        banco1.datosInicio();//Llamamos al metodo datos inicio y cargamos las cuentas de clientes

        do {
            try {
                String respuesta = menu();
                switch (respuesta) {
                    case "1": //CREAR CUENTA
                        crearCuenta(banco1);
                        break;
                    case "2": //ELIMINAR CUENTA
                        eliminarCuenta(banco1);
                        break;
                    case "3": //LISTAR CUENTAS
                        listarCuentas(banco1);
                        break;
                    case "4":// LOCALIZAR CUENTA
                        localizarCC(banco1);
                        break;
                    case "5": //OPERAR CUENTAS
                        operar(banco1);
                        break;
                    case "0": //SALIR
                        System.out.println("Gracias por usar nuestra aplicacion");
                        return;
                    default:
                        System.out.println("Debe seleccionar un numero correcto");
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Debe introducir un número");
                System.out.println();
            }
        } while (true);

    }

    public static String menu() {
        String respuesta;
        System.out.println("Menú General Banco de Valecia");
        System.out.println("GESTION DE CUENTA BANCARIA");
        System.out.println("1-Crear Cuenta");
        System.out.println("2-Eliminar Cuenta");
        System.out.println("3-Listar cuentas existentes");
        System.out.println("4-Localizar cuenta");
        System.out.println("5-Operar cuenta existente");
        System.out.println("0-Salir\n");

        respuesta = sc.nextLine();
        return respuesta;
    }

    public static String submenu() {
        String opcion;
        System.out.println();
        System.out.println("Bienvenido al Banco de Valencia ");
        System.out.println();
        System.out.println("1 - Ingresar Dinero");
        System.out.println("2- Retirar Dinero");
        System.out.println("3 - Autorizar Persona");
        System.out.println("4 - Desautorizar Persona");
        System.out.println("5 - Ver Información de cuenta");
        System.out.println("6 - Volver al menú anterior");
        System.out.println("  Introduce una opción:");
        opcion = sc.nextLine();

        return opcion;
    }

    public static void ingresar(CuentaBancaria cuenta) { //Paso por referencia del objeto cuenta (es el original)      
        double cantidad, saldo;
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Cuánto dinero desea ingresar?");
        cantidad = Double.parseDouble(sc.nextLine());
        saldo = cuenta.ingresar(cantidad);
        System.out.println("Se ha ingresado: " + cantidad + "€. Su saldo total es de: " + saldo + "€");
        System.out.println();

    }

    public static void sacar(CuentaBancaria cuenta) {
        double cantidad, saldo;
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cuanto dinero desea sacar?");
        cantidad = Double.parseDouble(sc.nextLine());

        if (cantidad <= cuenta.getSaldo()) {
            saldo = cuenta.sacar(cantidad);
            System.out.println("Se ha sacado: " + cantidad + "€. Su saldo total es de: " + cuenta.getSaldo() + "€");

        } else {
            System.out.println("No hay suficiente dinero en la cuenta para sacar " + cantidad + "€");
        }

        System.out.println();
    }

    public static void verInformacion(CuentaBancaria cuenta) {
        System.out.println(cuenta.informacionCuenta());
        //System.out.println(cuenta.verAutorizados());
    }

    public static void autorizar(CuentaBancaria cuenta) {
        String nif, nombre;
        Scanner sc = new Scanner(System.in);

        System.out.println("Nif de la persona que desea autorizar: ");
        nif = sc.nextLine();
        System.out.println("Nombre de la persona que desea autorizar: ");
        nombre = sc.nextLine();

        if (cuenta.autorizar(nif, nombre)) {
            System.out.println("Se ha autorizado a: " + nombre);
        } else {
            System.out.println("No ha sido posible autorizar al cliente: " + nombre + ", ya está autorizada en la cuenta");
        }

    }

    public static void desautorizar(CuentaBancaria cuenta) {
        String nif;
        //boolean desautorizado = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nif de la persona que desea desautorizar");
        nif = sc.nextLine();

        if (cuenta.desautorizar(nif)) {
            System.out.println("Se ha desautorizado al cliente con dni: " + nif);
        } else {
            System.out.println("No ha sido posible desautorizar al cliente con dni: " + nif + ", no estaba autorizado en la cuenta");
        }

        //System.out.println(cuenta.verAutorizados());
    }

    public static void crearCuenta(Banco banco) {

        String nif, nombre;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nif del cliente");
        nif = sc.nextLine();
        System.out.println("Introduzca el nombre del cliente");
        nombre = sc.nextLine();

        CuentaBancaria cuentaCreada = banco.crearCuenta(nif, nombre);

        System.out.println("Se ha creado a cuenta : " + cuentaCreada.getNumCuenta() + " con un saldo de regalo de : " + cuentaCreada.getSaldo());

    }

    public static void eliminarCuenta(Banco banco1) {

        System.out.println("Introduce Nº de Cuenta que quiere quiere eliminar: ");
        long ncuenta = Long.parseLong(sc.nextLine());
        if (banco1.existeCuenta(ncuenta)) {
            int resultado = banco1.eliminarCuenta(ncuenta);
            switch (resultado) {
                case -2:
                    System.out.println("Cuenta " + ncuenta + " no se ha podido eliminar "
                            + "porque dispone de saldo.\n");

                    CuentaBancaria CuentaOriginal = banco1.localizarCC(ncuenta);
                    double saldoOriginal = CuentaOriginal.getSaldo();//cuenta que quiero eliminar
                    System.out.println("Procedemos a transferir su saldo a otra cuenta para poder eliminar esta");
                    System.out.println("Indique cuenta destino");
                    long numeroCuentaDestino = Long.parseLong(sc.nextLine());
                    CuentaBancaria CuentaDestino = banco1.localizarCC(numeroCuentaDestino); //cuenta nueva a la que quiero ingresar

                    CuentaOriginal.sacar(saldoOriginal);
                    CuentaDestino.ingresar(saldoOriginal);

                    banco1.eliminarCuenta(ncuenta);

                    System.out.println("El saldo ha sido transferido correctamente \n " + "La cuenta " + CuentaDestino.getNumCuenta() + "tiene un saldo total de : " + CuentaDestino.getSaldo());

                    break;
                case -1:
                    System.out.println("La cuenta con el Nº: " + ncuenta + " no existe.\n");
                    break;
                case 0:
                    System.out.println("Se ha eliminado correctamente la cuenta: " + ncuenta + "\n");
            }
        } else {
            System.out.println("Ha habido un error. \nDisculpe las molestias");
        }
    }

    public static void listarCuentas(Banco banco1) {
        System.out.println("Introduzca el dni del titular");
        String nif = sc.nextLine();
        Set<CuentaBancaria> listado;

        listado = banco1.listarCuentas(nif);
        if (listado.isEmpty()) {
            System.out.println("No se han encontrado cuentas con el dni proporcionado: " + nif);

        } else {
            for (CuentaBancaria cuenta : banco1.listarCuentas(nif)) {
                System.out.println("Titular " + cuenta.getTitular() + "Numero de cuentas: " + cuenta.getNumCuenta() + " Saldo: " + cuenta.getSaldo() + " €");

            }
        }

        
    } 



public static void localizarCC(Banco banco1) {

        
        long numCuenta;
        System.out.println("Introduce Nº de Cuenta: ");
        numCuenta = Long.parseLong(sc.nextLine());
        if (banco1.existeCuenta(numCuenta)) {
            cuenta = banco1.localizarCC(numCuenta);
            System.out.println("Bienvenido a su cuenta: " + numCuenta + ". " + cuenta.getTitular());
        } else {
            System.out.println("No existe ninguna cuenta con el numero facilitado. \n Disculpe las molestias.");

        }

    }
    public static void operar(Banco banco1) {
        System.out.println("Por favor indica el numero de cuenta sobre el que quieres operar: ");
        long ncuenta = Long.parseLong(sc.nextLine());
        CuentaBancaria cuentaOperar = banco1.localizarCC(ncuenta);
        if (cuentaOperar == null) {
            System.out.println("No ha sido posible localizar la cuenta \n");

        } else {
            String res = "1";
            do {
                res = submenu();
                switch (res) {
                    case "1":
                        ingresar(cuentaOperar);
                        break;
                    case "2":
                        sacar(cuentaOperar);
                        break;
                    case "3":
                        autorizar(cuentaOperar);
                        break;
                    case "4":
                        desautorizar(cuentaOperar);
                        break;
                    case "5":
                        verInformacion(cuentaOperar);
                        break;
                    case "6":
                        menu();
                        return;
                    default:
                        System.out.println("Debe seleccionar un numero correcto");
                        break;

                }

            } while (!"0".equals(res));

        }
    }

}
