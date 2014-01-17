/*
 * s_rmifs
 * servidor_archivos
 *
 *
 *
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.Scanner;

public class s_rmifs {
  private static String puerto_archivos = "20912";
  private static String puerto_autenticacion = "20337";
  private static String direccion_autenticacion;
  private static s_rmifs_interfaz a_usuario;

 /*
  * Primero crea un Registry en el puerto_archivo, luego
  * crea una nueva instancia de s_rmifs_implementacion 
  * llamada a_usuario que usaremos para que el usuario
  * pueda ejecutar ciertos metodos, y luego registra 
  * con el nombre s_rmifs_Service al objeto a_usuario 
  * que se encuentra en el host direccion_autenticacion
  * y puerto puerto_archivo
  *
  * @throws Exception e 
  *
  */
  public s_rmifs() {
    try {
      LocateRegistry.createRegistry(Integer.parseInt(puerto_archivos));
      a_usuario = new s_rmifs_implementacion(direccion_autenticacion, puerto_autenticacion);
      Naming.rebind("rmi://localhost:"+puerto_archivos+"/s_rmifs_Service", a_usuario);
    }
    catch (Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

 /*
  * Lee la linea suministrada por el servidor y se la pasa a
  * ejecutar_comando para que la ejecute.
  *
  */
  public static void interpretar_comandos_teclado() {
    Scanner sc;
    sc = new Scanner(System.in);
    System.out.println("Introduzca su comando:\n");
    while(sc.hasNext()) {
      ejecutar_comando(sc.next().trim());
    }
  }

  public static void log() {
    try {
      a_usuario.imprimir_historial(20);
    }
    catch(RemoteException e) {
      System.out.println("Error de comunicacion con el servidor de archivos.");
    }
  }

 /*
  * Lista los comandos disponibles para el servidor
  *
  */
  public static void info() {
    System.out.println("Comandos disponibles:\n"
      +"log : Muestra en pantalla los ultimos 20 comandos ejecutados.\n"
      +"info : Muestra en pantalla esta ayuda.\n"
      +"sal : Termina la ejecucion del programa.\n");
  }

 /*
  * Ejecuta la instruccion suministrada, en el caso de ser incorrecta
  * lo notifica en pantalla
  *
  * @param instruccion: la instruccion que recibio el servidor 
  *
  */
  public static void ejecutar_comando(String instruccion) {
    if(instruccion.equals("log")) {
      log();
    }
    else if(instruccion.equals("sal")) {
      System.exit(0);
    }
    else if(instruccion.equals("info")) {
      info();
    }
    else {
      System.out.println("Comando invalido. Por favor introduzca un comando valido.");
    }
  }

 /*
  * Interpreta los argumentos y guarda el puerto de archivos suministrado
  * la direccion para la autenticacion y el puerto para la autenticacion 
  * ademas llama a s_rmifs
  *
  * @throws ArgumentosException si el numero de argumentos es menor que 3 o si no puede hacer las asignaciones correspondientes
  *
  */
  public static void interpretar_argumentos(String[] args) {
    try {
      if(args.length > 2) {
        if(args[0].equals("-l") && args[2].equals("-h") && args[4].equals("-r")) {
          puerto_archivos = args[1];
          direccion_autenticacion = args[3];
          puerto_autenticacion = args[5];
          new s_rmifs();
          interpretar_comandos_teclado();
        }
        else {
          throw new ArgumentosException();
        }
      }
      else {
        throw new ArgumentosException();
      }
    }
    catch(ArgumentosException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
  }

 /*
  * main
  * Con los argumentos llama a interpretar_argumentos 
  *
  * @param args Contiene los argumentos pasados por consola
  *
  */
  public static void main(String[] args) {
    interpretar_argumentos(args);
  }
}
