import java.rmi.Remote;
import java.rmi.RemoteException;

public interface a_rmifs_interfaz extends Remote {
  public void obtener_usuario_teclado() throws RemoteException;
  //public boolean validar(String nombre, String clave);
}
