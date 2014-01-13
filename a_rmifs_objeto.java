import java.util.Scanner;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class a_rmifs_objeto extends UnicastRemoteObject
  implements a_rmifs_interfaz {

  public validador_usuario usuario;

  public a_rmifs_objeto() throws RemoteException {
    super();
  }
  
  public void obtener_usuario_teclado() throws RemoteException {
    Scanner sc;
    String nombre, clave;

    sc = new Scanner(System.in);
    System.out.print("Nombre: ");
    nombre = sc.next();
    System.out.print("Clave: ");
    clave = sc.next();

    usuario = new validador_usuario(nombre,clave);
  }

}
