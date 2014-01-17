/*
 * s_rmifs_implementacion
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
import java.util.Dictionary;
import java.util.Hashtable;

/*
 * Implementacion del objeto remote que sirve de comunicacion entre el cliente
 * y el servidor de archivos.
 */
public class s_rmifs_implementacion extends UnicastRemoteObject
  implements s_rmifs_interfaz {

  private HistorialUsuarios historial;
  private Dictionary<String, String> propietarios;
  private a_rmifs_interfaz a_usuario;

 /* 
  * Crea una instancia del objeto de comunicacion cliente-servidor archivos.
  *
  * @param direccion: Nombre DNS o direccion IP del computador que aloja al
  *                   servidor de autenticacion.
  * @param puerto: Puerto habilitado por el servidor de autenticacion para
  *                ofrecer su servicio.
  *
  * @throws Exception e: Error de comunicacion entre el servidor de archivos
  *                      y el servidor de autenticacion.
  */
  public s_rmifs_implementacion(String direccion, String puerto)
    throws RemoteException {

    super();
    this.propietarios = new Hashtable<String,String>();
    this.historial = new HistorialUsuarios();
    try {
      a_usuario = (a_rmifs_interfaz)
        Naming.lookup("rmi://"+direccion+":"+puerto+"/a_rmifs_Service");
    }
    catch(Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

 /*
  * Devuelve un boolean que dice si se valido o no el usuario suministrado
  *
  * @param nombre: el nombre suministrado
  * @param clave: la clave suministrada
  * @throws RemoteException 
  * @return false si no se encuentra y true de lo contrario 
  *
  */
  public boolean validar(String nombre, String clave) throws RemoteException {
    return a_usuario.validar(nombre,clave);
  }

 /*
  * Muestra los archivos disponibles en la carpeta del servidor
  *
  * @throws RemoteException 
  * @return mensaje: string que contiene la lista de archivos del servidor 
  */
  public String rls() throws RemoteException {
    File directorio_actual;
    File[] archivos;
    String mensaje;

    mensaje = "";
    directorio_actual = new File(System.getProperty("user.dir"));
    archivos = directorio_actual.listFiles();
    for(int i=0; i<archivos.length; i++) {
      mensaje = mensaje+archivos[i].getName()+"\n";
    }

    return mensaje;
  }

 /* 
  * Eliminar un archivo en la carpeta del servidor
  *
  * @param nombre_usuario: el usuario que escribe el comando
  * @param archivo: el nombre del archivo que desea eliminar
  * @throws RemoteException
  */
  public boolean bor(String nombre_usuario, String archivo) throws RemoteException {
    File archivo_borrar;
    
    if(this.propietarios.get(archivo) != null &&
       this.propietarios.get(archivo).equals(nombre_usuario)) {
      archivo_borrar = new File(archivo);
      return archivo_borrar.delete();
    }
    return false;
  }

  /*
  * Subir archivo al servidor el cual se genera mediante un arreglo de bytes que se  
  * le suministra en los parametros por parte del cliente
  *
  * @param nombre_usuario: nombre del ususario que sube el archivo
  * @param nombre_archivo: nombre del archivo que se desea subir 
  * @param bytes_archivo: arreglo que contiene el archivo en bytes
  * @throws RemoteException
  *
  */
  public void sub(String nombre_usuario, String nombre_archivo,
    byte[] bytes_archivo) throws RemoteException {
    File archivo_guardado;
    FileOutputStream stream;
    try {
      this.propietarios.put(nombre_archivo, nombre_usuario);
      archivo_guardado = new File(nombre_archivo);
      stream = new FileOutputStream(archivo_guardado);
      stream.write(bytes_archivo);
      stream.flush();
      stream.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo a subir no fue encontrado.");
    }
    catch(IOException e) {
      System.out.println("Error de E/S en el archivo a subir.");
    }
  }

  /*
  * Bajar archivo del servidor por parte del cliente, el cual recibe
  * un arreglo de bytes y genera el archivo nuevamente con el nombre 
  * del archivo suministrado por el cliente
  *
  * @param archivo: nombre del archivo que se desea descargar 
  * @throws RemoteException
  *
  */
  public byte[] baj(String archivo) throws RemoteException {
    File archivo_descarga;
    FileInputStream stream;
    ByteArrayOutputStream b_array;
    byte[] buffer;
    byte[] buffer_descarga;

    try {
      archivo_descarga = new File(archivo);
      stream = new FileInputStream(archivo_descarga);
      b_array = new ByteArrayOutputStream();
      buffer = new byte[1024];
      
      for(int lectura; (lectura = stream.read(buffer)) != -1;) {
        b_array.write(buffer, 0, lectura);
      }
      buffer_descarga = b_array.toByteArray();
      return buffer_descarga;
    }
    catch(IOException e) {
      System.out.println("Error de E/S en el archivo a descargar.");
    }

    return null;
  }

 /* 
  * Agrega una instruccion nueva al historial de comandos
  *
  * @param usuario: el usuario que escribe el comando
  * @param instruccion: la instruccion suministrada por el usuario
  *
  * @throws RemoteException
  */
  public void agregar_instruccion(String usuario, String instruccion) throws RemoteException {
    this.historial.agregar_instruccion(usuario, instruccion);
  }

 /*
  * Imprime por pantalla del servidor de archivos el historial de comandos
  * utilizados, con su respectivo usuario.
  *
  * @param num_instrucciones: numero de instrucciones a imprimir por pantalla.
  *
  * @hrows RemoteException
  */
  public void imprimir_historial(int num_instrucciones) throws RemoteException {
    this.historial.imprimir_historial(num_instrucciones);
  }

}
