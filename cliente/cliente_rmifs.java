import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class cliente_rmifs {
  private static String puerto = "210912";
  private static validador_usuario usuario;

  public static void obtener_usuario_teclado() {
    Scanner sc;
    String nombre, clave;

    sc = new Scanner(System.in);
    System.out.print("Nombre: ");
    nombre = sc.next();
    System.out.print("Clave: ");
    clave = sc.next();

    usuario = new validador_usuario(nombre,clave);
  }

  private static void obtener_usuario_archivo(File archivo) {
    Scanner sc;
    String linea;
    String[] dupla;
    
    try {
      sc = new Scanner(archivo);
      while(sc.hasNextLine()) {
        linea = sc.nextLine();
        dupla = linea.split(":");
        //Por defecto se tendrá que el ultimo usuario leido en el archivo sea
        //el que tome el sistema para la autenticacion.
        usuario = new validador_usuario(dupla[0],dupla[1]);
      }
      sc.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo de datos de los usuarios no fue encontrado.");
    }

  }

  private static void interpretar_argumentos(String[] args) {
    try {
      if(args[0].equals("-f")) {
        obtener_usuario_archivo(new File(args[1]));
        //Java no evalua toda la expresion, con que el primero se haga false,
        //no sigue evaluando (evaluacion perezosa, quizas?)
        if(args.length > 2 && args[2].equals("-p")) {
          puerto = args[3];
        }
      }
      if(args[0].equals("-p")) {
        obtener_usuario_teclado();
        puerto = args[1];
      }
    }
    catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
  }
  
  public static void main(String[] args) {
    try {
      a_rmifs_interfaz a_usuario = (a_rmifs_interfaz)
        //Esta opcion comentada debe ser probada en la Universidad, 
        //por la lejania del servidor tarda en responder.
        //Naming.lookup("rmi://irulan.ldc.usb.ve:21000/a_rmifs_Service");
        Naming.lookup("rmi://localhost:21000/a_rmifs_Service");
      interpretar_argumentos(args);
      //PRUEBAS
      System.out.println(a_usuario.validar(usuario.getNombre(),usuario.getClave()));
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
