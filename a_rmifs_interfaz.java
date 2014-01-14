import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface a_rmifs_interfaz extends Remote {
  public boolean validar(String nombre, String clave) throws RemoteException;
  public void suscribir_usuarios(File archivo) throws RemoteException;
}
