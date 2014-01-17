/*
 * s_rmifs_interfaz
 *
 * @ Grupo 50
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Interfaz que define el comportamiento del objeto que sirve de comunicacion
 * entre el servidor de archivos central y el programa cliente.
 */
public interface s_rmifs_interfaz extends Remote {

 /*
  * Devuelve un boolean que dice si se valido o no el usuario suministrado
  *
  * @param nombre: el nombre suministrado
  * @param clave: la clave suministrada
  * @throws RemoteException 
  * @return false si no se encuentra y true de lo contrario 
  *
  */
  public boolean validar(String nombre, String clave) throws RemoteException;

 /*
  * Muestra los archivos disponibles en la carpeta del servidor
  *
  * @throws RemoteException 
  */
  public String rls() throws RemoteException;

 /* 
  * Eliminar un archivo en la carpeta del servidor
  *
  * @param nombre_usuario: el usuario que escribe el comando
  * @param archivo: el nombre del archivo que desea eliminar
  * @throws RemoteException
  */
  public boolean bor(String nombre_usuario, String archivo) throws RemoteException;
 
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
  public void sub(String nombre_usuario, String nombre_archivo, byte[] bytes_archivo)
    throws RemoteException;

 /*
  * Bajar archivo del servidor por parte del cliente, el cual recibe
  * un arreglo de bytes y genera el archivo nuevamente con el nombre 
  * del archivo suministrado por el cliente
  *
  * @param archivo: nombre del archivo que se desea descargar 
  * @throws RemoteException
  *
  */
  public byte[] baj(String archivo) throws RemoteException;

 /* 
  * Agrega una instruccion nueva al historial de comandos
  *
  * @param usuario: el usuario que escribe el comando
  * @param instruccion: la instruccion suministrada por el usuario
  *
  * @throws RemoteException
  */
  public void agregar_instruccion(String usuario, String instruccion) throws RemoteException;

/*
  * Imprime por pantalla del servidor de archivos el historial de comandos
  * utilizados, con su respectivo usuario.
  *
  * @param num_instrucciones: numero de instrucciones a imprimir por pantalla.
  *
  * @hrows RemoteException
  */
  public void imprimir_historial(int num_instrucciones) throws RemoteException;
}
