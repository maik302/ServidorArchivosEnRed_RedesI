import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class cliente_rmifs {
  private static String servidor;
  private static String puerto = "210912";
  private static validador_usuario usuario;

  private static void interpretar_argumentos(String[] args) {
    int i;

    try {
      i = 0;
      if(args[0].equals("-f")) {
        usuario = obtener_usuario_archivo(new File(args[1]));
      }
      else {
        i = 2;
        usuario = obtener_usuario_teclado();
        if(args[i].equals("-m")) {
          servidor = args[i+1];
          if(args[i+2].equals("-p")) {
            puerto = args[i+3];
          }
          else {
            throw new ArgumentosException();
          }
          //Java no evalua toda la expresion, con que el primero se haga false,
          //no sigue evaluando (evaluacion perezosa, quizas?)
          if(args.length > i+4 && args[i+4].equals("-c")) {
            interpretar_comandos_archivo(new File(args[i+5]));
          }
          interpretar_comandos_teclado();
          
        }
        else {
          throw new ArgumentosException();
        }
      }
    }
    catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
    catch(ArgumentosException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
    catch(FileNotFoundException e) {
      System.out.println("Archivo no encontrado.");
      System.exit(0);
    }

  }
  
  public static void main(String[] args) {
    try {
      a_rmifs_interfaz a_usuario = (a_rmifs_interfaz)
        //Esta opcion comentada debe ser probada en la Universidad, 
        //por la lejania del servidor tarda en responder.
        //Naming.lookup("rmi://irulan.ldc.usb.ve:21000/a_rmifs_Service");
        Naming.lookup("rmi://"+servidor+":"+puerto+"/a_rmifs_Service");
      interpretar_argumentos(args);
      //PRUEBAS

    }
    catch (MalformedURLException murle) {
      System.out.println();
      System.out.println("MalformedURLException");
      System.out.println(murle);
    }
    catch (RemoteException re) {
      System.out.println();
      System.out.println("RemoteException");
      System.out.println(re);
    }
    catch (NotBoundException nbe) {
      System.out.println();
      System.out.println("NotBoundException");
      System.out.println(nbe);
    }
  }

}
