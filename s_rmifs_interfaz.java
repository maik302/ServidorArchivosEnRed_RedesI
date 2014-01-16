import java.io.ByteArrayOutputStream;
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface s_rmifs_interfaz extends Remote {
  public boolean validar(String nombre, String clave) throws RemoteException;
  public void rls() throws RemoteException;
  public boolean bor(String nombre_usuario, String archivo) throws RemoteException;
  public void sub(String nombre_usuario, String nombre_archivo, byte[] bytes_archivo)
    throws RemoteException;
  public byte[] baj(String archivo) throws RemoteException;
  public void agregar_instruccion(String usuario, String instruccion) throws RemoteException;
}
