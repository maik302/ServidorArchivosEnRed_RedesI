public class s_rmifs_implementacion extends UnicastRemoteObject
  implements s_rmifs_interfaz {

  private HistorialUsuarios historial;
  private String usuario;
  private a_rmifs_interfaz a_usuario;

  public s_rmifs_implementacion(String direccion, String puerto)
    throws RemoteException {

    super();
    historial = new HistorialUsuarios;
    a_usuario = (a_rmifs_interfaz)
      Naming.lookup("rmi://"+direccion+":"+puerto+"a_rmifs_Service");
  }

  public boolean validar(String nombre, String clave) {
    return a_usuario.validar(nombre,clave);
  }

  public void rls() {
    File directorio_actual;
    File[] archivos;

    directorio_actual = new File(System.getProperty("user.dir"));
    archivos = directorio_actual.listFiles();
    for(int i=0; i<archivos.length; i++) {
      System.out.println(archivos[i].getName());
    }
  }

  public boolean bor(String archivo) {
    File archivo;

    archivo = new File(archivo);
    return archivo.delete();
  }

  public void agregar_instruccion(String instruccion) {
    historial.agregar_instruccion(usuario, instruccion);
  }

}
