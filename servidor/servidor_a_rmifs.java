import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class servidor_a_rmifs {

  public servidor_a_rmifs() {
    try {
      //Se utiliza la siguiente instruccion para no tener que ejecutar
      //rmiregustry en el PATH donde se encuentra el objeto remoto.
      LocateRegistry.createRegistry(21000);
      a_rmifs_interfaz a_usuario = new a_rmifs_objeto();
      //La siguiente linea comentada debe ser ejecutada en la Universidad,
      //por la lejania del servidor la operacion tarda considerablemente.
      //Naming.rebind("rmi://irulan.ldc.usb.ve:21000/a_rmifs_Service", a_usuario);
      Naming.rebind("rmi://localhost:21000/a_rmifs_Service", a_usuario);
      a_usuario.suscribir_usuarios(new File("usuarios.txt"));
    }
    catch (Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

  public static void main(String[] args) {
    new servidor_a_rmifs();
  }
}
