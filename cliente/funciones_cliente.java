/*
 * funciones_cliente
 * cliente
 *
 * @ Grupo 50
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Scanner;

/*
 * Funciones necesarias para la ejecucion del programa cliente.
 */
public class funciones_cliente {

  private static s_rmifs_interfaz a_usuario;
  private static String nombre_usuario;

 /*
  * Ejecuta el registry con el puerto_autenticacion
  * Crea una nueva instancia de s_rmifs_implementacion 
  * llamada a_usuario que usaremos para que el usuario
  * pueda ejecutar ciertos metodos
  *
  * @throws Exception e: Cuando no puede establercer la conexion 
  *
  */
  public static void establecer_conexion(String servidor, String puerto) {
    try {
      a_usuario = (s_rmifs_interfaz)
        Naming.lookup("rmi://"+servidor+":"+puerto+"/s_rmifs_Service");
    }
    catch(Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }
  
 /*
  * Valida el usuario directamente suministrado por
  * consola
  *
  */
  public static validador_usuario obtener_usuario_teclado() {
    Scanner sc;
    String clave_usuario;
    sc = new Scanner(System.in);
    System.out.print("Nombre: ");
    nombre_usuario = sc.next();	
    System.out.print("Clave: ");
    clave_usuario = sc.next();
    return new validador_usuario(nombre_usuario, clave_usuario);
  }

 /*
  * Valida el usuario desde el archivo suministrad, por
  * defecto se tendrá que el ultimo usuario leido en el 
  * archivo sea el que tome el sistema para la autenticacion.
  * 
  * @param archivo: archivo suministrado por el cliente con su usuario y clave
  * @throws FileNotFoundException: No se encuentra el archivo de usuarios
  * @return true si lo valido, false si no y null si ocurrio un error 
  *
  */
  public static validador_usuario obtener_usuario_archivo(File archivo) {
    Scanner sc;
    String linea;
    String[] dupla;  
    try {
      sc = new Scanner(archivo);
      while(sc.hasNextLine()) {
	      linea = sc.nextLine();
	      dupla = linea.split(":");
        nombre_usuario = dupla[0];
	      return new validador_usuario(dupla[0],dupla[1]);
      }
      sc.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo de datos de los usuarios no fue encontrado.");
    }
    return null;
  }

 /*
  * Devuelve un boolean que dice si se valido o no el usuario suministrado
  * para ello utiliza validador_usuario en su llamada
  *
  * @param validador_usuario usuario: recibe el usuario
  * @throws RemoteException e: si ocurre un error ejecutando getNombre() o getClave() 
  * @return respuesta: false si no se encuentra y true de lo contrario 
  *
  */
  public static boolean validar(validador_usuario usuario) {
    boolean respuesta = false;
    try {
      respuesta = a_usuario.validar(usuario.getNombre(), usuario.getClave());
    }
    catch(RemoteException e) {
      System.out.println("Error en la validacion del usuario.");
    }
    
    return respuesta;
  }

 /*
  * Lee el comando del archivo suministrado y se lo pasa a ejecutar_comando
  * en un arreglo del tamaño de la cantidad de palabras que tenga la 
  * instruccion
  * 
  * @param archivo: archivo que contiene comando para que sean ejecutados
  *
  */
  public static void interpretar_comandos_archivo(File archivo) {
    Scanner sc;
    String linea;
    String[] dupla;
    try {
      sc = new Scanner(archivo);
      while(sc.hasNextLine()) {
	      linea = sc.nextLine();
	      dupla = linea.split("\\s+");
	      ejecutar_comando(dupla);
      }
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo de datos de los comandos no fue encontrado");
    }
  }


 /*
  * Lee el comando en pantalla y se lo pasa a ejecutar_comando en
  * un arreglo del tamaño de la cantidad de palabras que tenga la 
  * instruccion
  *
  */
  public static void interpretar_comandos_teclado() {
    Scanner sc;
    String comando;
    String [] dupla;
    sc = new Scanner(System.in);
    System.out.println("Introduzca su comando:\n");
    while(sc.hasNextLine()) {
      comando = sc.nextLine();
      dupla = comando.split("\\s+");
      ejecutar_comando(dupla);
    }
  }

 /*
  * Lista los comandos disponibles para el servidor
  *
  */
  public static void info() {
    System.out.println("Comandos disponibles:\n"
      +"rls : Muestra la lista de archivos disponibles en el servidor.\n"
      +"lls : Muestra la lista de archivos locales disponibles.\n"
      +"sub archivo : Sube archivo al servidor remoto.\n"
      +"baj archivo : Descarga archivo del servidor remoto.\n"
      +"bor archivo : Borra archivo del servidor remoto.\n"
      +"info : Muestra en pantalla esta ayuda.\n"
      +"sal : Termina la ejecucion del programa.\n");
  }


 /*
  * Muestra los archivos disponibles en la carpeta del cliente
  *
  */
  public static void lls() {
    File directorio_actual;
    File[] archivos;
    directorio_actual = new File(System.getProperty("user.dir"));
    archivos = directorio_actual.listFiles();
    for(int i=0; i<archivos.length; i++) {
      System.out.println(archivos[i].getName());
    } 
  }


 /*
  * Subir archivo del servidor por parte del cliente, el cual genera 
  * un arreglo de bytes usando el archivo suministrado
  *
  * @param nombre_archivo: nombre del archivo que se desea subir 
  * @throws IOException: Hubo un problema de E/S en el archivo
  *
  */
  public static void sub(String archivo) {
    File archivo_subida;
    FileInputStream stream;
    ByteArrayOutputStream b_array;
    byte[] buffer;
    byte[] buffer_subida;

    try {
      archivo_subida = new File(archivo);
      stream = new FileInputStream(archivo_subida);
      b_array = new ByteArrayOutputStream();
      buffer = new byte[1024];
      
      for(int lectura; (lectura = stream.read(buffer)) != -1;) {
        b_array.write(buffer, 0, lectura);
      }
      buffer_subida = b_array.toByteArray();
      a_usuario.sub(nombre_usuario, archivo, buffer_subida);

    }
    catch(IOException e) {
      System.out.println("Error en la lectura del archivo a subir.");
    }
  }

 /*
  * Bajar archivo del servidor por parte del cliente, el cual recibe
  * un arreglo de bytes y genera el archivo nuevamente con el nombre 
  * del archivo suministrado por el cliente
  *
  * @param nombre_archivo: nombre del archivo que se desea descargar 
  * @throws FileNotFoundException: El archivo no fue encontrado
  * @throws IOException: Hubo un problema de E/S en el archivo
  *
  */
  public static void baj(String nombre_archivo) {
    File archivo_guardado;
    FileOutputStream stream;
    ByteArrayOutputStream b_array;
    byte[] buffer_descarga;
    try {
      buffer_descarga = a_usuario.baj(nombre_archivo);
      archivo_guardado = new File(nombre_archivo);
      stream = new FileOutputStream(archivo_guardado);
      stream.write(buffer_descarga);
      stream.flush();
      stream.close();   
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo a descargar no fue encontrado.");
    }
    catch(IOException e) {
      System.out.println("Error de E/S en el archivo a descargar.");
    }
  }

 /*
  * Ejecuta la instruccion pasada, si es un comando valido ejecuta alguna de las
  * opciones y si no puede ejecutar o enviar informacion devuelve un error  
  * 
  * @param instruccion: la instruccion pasada por el cliente
  * @throws RemoteException: Ha ocurrido un error en la comunicacion con el servidor
  *
  */
  public static void ejecutar_comando(String[] instruccion) {
    try {
      if(instruccion[0].equals("rls")) {
        a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
  	    System.out.println(a_usuario.rls());
      }
      if(instruccion[0].equals("lls")) {
	      a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        lls();
      }
      if(instruccion[0].equals("sub")) {
	      a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        sub(instruccion[1]);
      }
      if(instruccion[0].equals("baj")) {
	      a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        baj(instruccion[1]);
      }
      if(instruccion[0].equals("bor")) {
        a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
	      if(a_usuario.bor(nombre_usuario, instruccion[1])) {
          System.out.println("El archivo "+instruccion[1]+" ha sido borrado.");
        }else{
          System.out.println("EL archivo "+instruccion[1]+" no pudo ser borrado.");
        }
      }
      if(instruccion[0].equals("info")) {
	      a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        info();
      }
      if(instruccion[0].equals("sal")) {
	      a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        System.exit(0);
      }
      else {
        throw new ArgumentosException();
      }
    }
    catch(ArgumentosException e) {
      System.out.println("Comando invalido, por favor introduzca un comando valido.");
    }
    catch(RemoteException e) {
      System.out.println("Ha ocurrido un error en la comunicacion con el servidor.");
    }
  }

}
