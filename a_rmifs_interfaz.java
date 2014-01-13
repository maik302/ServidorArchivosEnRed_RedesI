import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface a_rmifs_interfaz extends Remote {
  public boolean validar(validador_usuario usuario) throws RemoteException;
  public void suscribir_usuarios(File archivo) throws RemoteException;
}
