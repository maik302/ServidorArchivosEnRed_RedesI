/*
 * validador_usuario
 * cliente
 *
 *
 *
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.rmi.RemoteException;

public class validador_usuario {
  private String nombre;
  private String clave;

 /*
  * Le asigna los atributos a de nombre y clave al usuario
  *
  * @param nombre: nombre del usuario
  * @param clave:  clave del usuario
  *
  */
  public validador_usuario(String nombre, String clave) {
    this.nombre = new String(nombre);
    this.clave = new String(clave);
  }

 /*
  * Devuelve el nombre del usuario
  *
  * @return this.nombre: el nombre del usuario en cuestion
  *
  */
  public String getNombre() {
    return this.nombre;
  }

 /*
  * Devuelve la clave del usuario
  *
  * @return this.clave: la clave del usuario en cuestion
  *
  */
  public String getClave() {
    return this.clave;
  }

 /* 
  * Valida el usuario comparando nombre y clave, devolviendo un boolean
  *
  * @param nombre: nombre del usuario
  * @param clave:  clave del usuario
  * @return true si la validacion es correcta y un false si no
  *
  */
  public boolean validar(String nombre, String clave) 
    throws RemoteException {
    if (nombre.equals(this.nombre) && clave.equals(this.clave)) {
      return true;
    }
    return false;
  }

 /*
  * Devuelve una cadena de string con el nombre y clave del usuario
  *
  * @return Nombre: (Nombre del usuario) Clave: (Clave del usuario)
  *
  */
  public String toString() {
    return "Nombre: "+this.nombre+"\nClave: "+this.clave+"\n";
  }
}
