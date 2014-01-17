/*
 * a_rmifs_implementacion
 *
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Dictionary;
import java.util.Hashtable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class a_rmifs_implementacion extends UnicastRemoteObject
  implements a_rmifs_interfaz {

  private Dictionary<String,String> usuarios;

 /* 
  * Crea una instancia de Hashtable llamada Usuarios para guardar 
  * la lista de usuarios con sus respectivas claves
  * 
  * @throws RemoteException
  */
  public a_rmifs_implementacion() throws RemoteException {
    super();
    this.usuarios = new Hashtable<String,String>();
  }

 /* 
  * Valida el usuario comparando nombre y clave, devolviendo un boolean
  *
  * @param nombre: nombre del usuario
  * @param clave:  clave del usuario
  * @return true si la validacion es correcta y un false si no
  */
  public boolean validar(String nombre, String clave) 
    throws RemoteException {
    String clave_verificador;

    if(this.usuarios.get(nombre) != null) {
      clave_verificador = (String) this.usuarios.get(nombre);
      if(clave_verificador.equals(clave)) {
        return true;
      }
    }

    return false;
  }
  

 /* 
  * Suscribe a los usuarios con sus claves en forma de dupla en el sistema,
  * por defecto, si un usuario es agregado mas de una vez, su clave sera
  * sobreescrita por la ultima agregada
  *
  * @param archivo: recibe el archivo con los usuarios y sus claves
  * @throws FileNotFoundException: No encuentra el archivo suministrado
  */
  public void suscribir_usuarios(File archivo) throws RemoteException {
    Scanner sc;
    String linea;
    String[] dupla;
    
    try {
      sc = new Scanner(archivo);
      while(sc.hasNextLine()) {
        linea = sc.nextLine();
        dupla = linea.split(":");
        usuarios.put(dupla[0],dupla[1]);
      }
      sc.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo de datos de los usuarios no fue encontrado.");
    }
  }
}
