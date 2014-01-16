import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Dictionary;
import java.util.Hashtable;

public class s_rmifs_implementacion extends UnicastRemoteObject
  implements s_rmifs_interfaz {

  private HistorialUsuarios historial;
  private Dictionary<String, String> propietarios;
  private a_rmifs_interfaz a_usuario;

  public s_rmifs_implementacion(String direccion, String puerto)
    throws RemoteException {

    super();
    historial = new HistorialUsuarios();
    propietarios = new Hashtable<String,String>();
    try {
      a_usuario = (a_rmifs_interfaz)
        Naming.lookup("rmi://"+direccion+":"+puerto+"/a_rmifs_Service");
    }
    catch(Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

  public boolean validar(String nombre, String clave) throws RemoteException {
    return a_usuario.validar(nombre,clave);
  }

  public void rls() throws RemoteException {
    File directorio_actual;
    File[] archivos;

    directorio_actual = new File(System.getProperty("user.dir"));
    archivos = directorio_actual.listFiles();
    for(int i=0; i<archivos.length; i++) {
      System.out.println(archivos[i].getName());
    }
  }

  public boolean bor(String nombre_usuario, String archivo) throws RemoteException {
    File archivo_borrar;
    
    if(propietarios.get(archivo) != null &&
       propietarios.get(archivo).equals(nombre_usuario)) {
      archivo_borrar = new File(archivo);
      return archivo_borrar.delete();
    }
    return false;
  }

  public void sub(String nombre_usuario, String nombre_archivo,
    ByteArrayOutputStream bytes_archivo) throws RemoteException {
    byte[] archivo;
    File archivo_guardado;
    FileOutputStream stream;
    try {
      propietarios.put(nombre_archivo, nombre_usuario); 
      archivo = bytes_archivo.toByteArray();
      archivo_guardado = new File(nombre_archivo);
      stream = new FileOutputStream(archivo_guardado);
      stream.write(archivo);
      stream.flush();
      stream.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo a subir no fue encontrado.");
    }
    catch(IOException e) {
      System.out.println("Error de E/S en el archivo a subir.");
    }
  }

  public ByteArrayOutputStream baj(String nombre_archivo) throws RemoteException {
    File archivo_descarga;
    FileInputStream stream;
    ByteArrayOutputStream b_array;
    byte[] buffer;

    try {
      archivo_descarga = new File(nombre_archivo);
      stream = new FileInputStream(archivo_descarga);
      b_array = new ByteArrayOutputStream();
      buffer = new byte[1024];
      
      for(int lectura; (lectura = stream.read(buffer)) != -1;) {
        b_array.write(buffer, 0, lectura);
      }
      return b_array;
    }
    catch(IOException e) {
      System.out.println("Error de E/S en el archivo a desacargar.");
    }

    return null;
  }

  public void agregar_instruccion(String usuario, String instruccion) throws RemoteException {
    historial.agregar_instruccion(usuario, instruccion);
  }

}
