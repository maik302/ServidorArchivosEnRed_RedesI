import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class cliente_rmifs {
  private static String servidor;
  private static String puerto = "20912";
  private static String archivo_comandos = "";
  private static validador_usuario usuario;

  private static void interpretar_argumentos(String[] args) {
    int i;

    try {
      i = 0;
      if(args[0].equals("-f")) {
        usuario = funciones_cliente.obtener_usuario_archivo(new File(args[1]));
        i = 2;
      }
      else {
        usuario = funciones_cliente.obtener_usuario_teclado();
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
            archivo_comandos = args[5];
          }
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
      System.out.println("error en la especificacion de llamada del programa.");
      System.exit(0);
    }
    //catch(FileNotFoundException e) {
    //  System.out.println("Archivo no encontrado.");
    //  System.exit(0);
    //}

  }
  
  public static void main(String[] args) {
    try {
      interpretar_argumentos(args);
      a_rmifs_interfaz a_usuario = (a_rmifs_interfaz)
        Naming.lookup("rmi://"+servidor+":"+puerto+"/a_rmifs_Service");
      //Autenticacion del usuario
      if(a_usuario.validar(usuario.getNombre(),usuario.getClave())) {
        if(!archivo_comandos.equals("")) {
          interpretar_comandos_archivo(new File(archivo_comandos));
        }
        interpretar_comandos_teclado();
      }
      else {
        throw new AutenticacionException();
      }
      //PRUEBAS
      System.out.println(a_usuario.validar("maria","123"));

    }
    catch(AutenticacionException e) {
      System.out.println("La combinacion de usuario y clave no es válida.\n");
      System.exit(0);
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
