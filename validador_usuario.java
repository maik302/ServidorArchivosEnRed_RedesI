import java.rmi.RemoteException;

public class validador_usuario {
  private String nombre;
  private String clave;

  public validador_usuario(String nombre, String clave) {
    this.nombre = new String(nombre);
    this.clave = new String(clave);
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getClave() {
    return this.clave;
  }

  public boolean validar(String nombre, String clave) 
    throws RemoteException {
    if (nombre.equals(this.nombre) && clave.equals(this.clave)) {
      return true;
    }
    return false;
  }

  public String toString() {
    return "Nombre: "+this.nombre+"\nClave: "+this.clave+"\n";
  }
}
