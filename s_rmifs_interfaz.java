import java.io.ByteArrayOutputStream;
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface s_rmifs_interfaz extends Remote {
  public boolean validar(String nombre, String clave) throws RemoteException;
  public void rls() throws RemoteException;
  public boolean bor(String archivo) throws RemoteException;
  public void sub(String nombre_archivo, ByteArrayOutputStream bytes_archivo)
    throws RemoteException;
  public ByteArrayOutputStream baj(String nombre_archivo) throws RemoteException;
  public void agregar_instruccion(String instruccion) throws RemoteException;
}
