import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;

public class servidor_a_rmifs {
  private static String puerto = "20912";
  private static a_rmifs_interfaz a_usuario;

  public servidor_a_rmifs() {
    try {
      //Se utiliza la siguiente instruccion para no tener que ejecutar
      //rmiregustry en el PATH donde se encuentra el objeto remoto.
      LocateRegistry.createRegistry(Integer.parseInt(puerto));
      a_usuario = new a_rmifs_objeto();
      //La siguiente linea comentada debe ser ejecutada en la Universidad,
      //por la lejania del servidor la operacion tarda considerablemente.
      //Naming.rebind("rmi://irulan.ldc.usb.ve:21000/a_rmifs_Service", a_usuario);
      Naming.rebind("rmi://localhost:"+puerto+"/a_rmifs_Service", a_usuario);
    }
    catch (Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

  public static void interpretar_argumentos(String[] args) {
    try {
      if(args.length > 2) {
        if(args[0].equals("-f") && args[2].equals("-p")) {
          puerto = args[3];
          new servidor_a_rmifs();
          a_usuario.suscribir_usuarios(new File(args[1]));
        }
        else {
          throw new ArgumentosException();
        }
      }
      else {
        throw new ArgumentosException();
      }
    }
    catch(RemoteException e) {
      System.out.println("Se ha encontrado un error del tipo: "+e.getMessage());
    }
    catch(ArgumentosException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);

    }
  }

  public static void main(String[] args) {
    new servidor_a_rmifs();
  }
}
