/*
 * c_rmifs
 * Programa Cliente
 *
 * @ Grupo 50
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

/*
 * Clase que implementa el programa cliente.
 */
public class c_rmifs {
  private static String servidor;
  private static String puerto = "20912";
  private static String archivo_comandos = "";
  private static validador_usuario usuario;

 /*
  * Interpreta los argumentos, guardando los necesarios como el nombre o ip del
  * servidor, para establecer la conexion, guarda los puertos, y el nombre del 
  * archivo que contiene los comandos a ejecutarse 
  * 
  * @param args: los argumentos de la llamada al programa
  * @throws ArgumentosException: Error con los parametros suministrados en la 
  *				 llamada del programa
  * @throws ArrayIndexOutOfBoundsException: Error con los parametros suministrados en la 
  *					    llamada del programa
  *
  */
  private static void interpretar_argumentos(String[] args) {
    int i;
    try {
      i = 0;
      if(args[0].equals("-f")) {
        usuario = funciones_cliente.obtener_usuario_archivo(new File(args[1]));
        i = 2;
      }
      if(args[i].equals("-m")) {
        if(i==0){
          usuario = funciones_cliente.obtener_usuario_teclado();
        }
        servidor = args[i+1];
        if(args[i+2].equals("-p")) {
          puerto = args[i+3];
        }else{
          throw new ArgumentosException();
        }
        if(args.length > i+4 && args[i+4].equals("-c")) {
          archivo_comandos = args[i+5];
        }
      }
      else{
        throw new ArgumentosException();
      }
    }
    catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
    catch(ArgumentosException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
  }
  

 /*
  * Main
  * Recibe los argumentos de la llamada y se los pasa a
  * interpretar_argumentos, establecer_conexion, ademas de
  * validar al usuario, luego empieza a recibir comandos      
  * 
  * @param args: recibe los argumentos de la llamada
  * @throws AutenticacionException: Si el usuario,la clave o su combinacion no son validos.
  *
  */
  public static void main(String[] args) {
    try {
      interpretar_argumentos(args);
      funciones_cliente.establecer_conexion(servidor,puerto);
      if(!funciones_cliente.validar(usuario)) {
        throw new AutenticacionException();
      }
      if(!archivo_comandos.equals("")) {
        funciones_cliente.interpretar_comandos_archivo(new File(archivo_comandos));
      }
      funciones_cliente.interpretar_comandos_teclado();
    }
    catch(AutenticacionException e) {
      System.out.println("La combinacion de usuario y clave no es válida.\n");
      System.exit(0);
    }
  }

}
