import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.Scanner;

public class s_rmifs {
  private static String puerto_archivos = "20912";
  private static String puerto_autenticacion = "20337";
  private static String direccion_autenticacion;
  private static s_rmifs_interfaz a_usuario;

  public s_rmifs() {
    try {
      LocateRegistry.createRegistry(Integer.parseInt(puerto_archivos));
      a_usuario = new s_rmifs_implementacion(direccion_autenticacion, puerto_autenticacion);
      Naming.rebind("rmi://localhost:"+puerto_archivos+"/s_rmifs_Service", a_usuario);
    }
    catch (Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

  public static void interpretar_comandos_teclado() {
    Scanner sc;

    sc = new Scanner(System.in);
    System.out.println("Introduzca su comando:\n");
    while(sc.hasNext()) {
      ejecutar_comando(sc.next().trim());
    }
  }

  public static void info() {
    System.out.println("Comandos disponibles:\n"
      +"log : Muestra en pantalla los ultimos 20 comandos ejecutados.\n"
      +"info : Muestra en pantalla esta ayuda.\n"
      +"sal : Termina la ejecucion del programa.\n");
  }

  public static void ejecutar_comando(String instruccion) {
    if(instruccion.equals("log")) {
      //Historial
    }
    if(instruccion.equals("sal")) {
      System.exit(0);
    }
    if(instruccion.equals("info")) {
      info();
    }
    else {
      System.out.println("Comando invalido. Por favor introduzca un comando valido.");
    }
  }

  public static void interpretar_argumentos(String[] args) {
    try {
      if(args.length > 2) {
        if(args[0].equals("-l") && args[2].equals("-h") && args[4].equals("-r")) {
          puerto_archivos = args[1];
          direccion_autenticacion = args[3];
          puerto_autenticacion = args[5];
          //A partir de aqui se debe de implementar los metodos con hilos
          new s_rmifs();
        }
        else {
          throw new ArgumentosException();
        }
      }
      else {
        throw new ArgumentosException();
      }
    }
    catch(ArgumentosException e) {
      System.out.println("Error en la especificacion de llamada del programa.");
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    interpretar_argumentos(args);
  }
}
