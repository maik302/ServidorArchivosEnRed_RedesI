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

  public a_rmifs_implementacion() throws RemoteException {
    super();
    this.usuarios = new Hashtable<String,String>();
  }

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
  
  public void suscribir_usuarios(File archivo) throws RemoteException {
    Scanner sc;
    String linea;
    String[] dupla;
    
    try {
      sc = new Scanner(archivo);
      while(sc.hasNextLine()) {
        linea = sc.nextLine();
        dupla = linea.split(":");
        System.out.println(dupla[0]+"\n"+dupla[1]);
        //Por defecto, si un usuario es agregado mas de una vez, su
        //clave sera sobreescrita por la ultima agregada
        usuarios.put(dupla[0],dupla[1]);
      }
      sc.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo de datos de los usuarios no fue encontrado.");
    }
  }
}
