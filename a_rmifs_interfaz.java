/*
 * a_rmifs_interfaz
 *
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface a_rmifs_interfaz extends Remote {

 /* 
  * Valida el usuario comparando nombre y clave, devolviendo un boolean
  *
  * @param nombre: nombre del usuario
  * @param clave:  clave del usuario
  * @return true si la validacion es correcta y un false si no
  *
  */
  public boolean validar(String nombre, String clave) throws RemoteException;

 /* 
  * Suscribe a los usuarios con sus claves en forma de dupla en el sistema,
  * por defecto, si un usuario es agregado mas de una vez, su clave sera
  * sobreescrita por la ultima agregada
  *
  * @param archivo: recibe el archivo con los usuarios y sus claves
  * @throws FileNotFoundException: No encuentra el archivo suministrado
  */
  public void suscribir_usuarios(File archivo) throws RemoteException;
}
