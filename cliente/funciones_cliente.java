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

public class funciones_cliente {

  private static s_rmifs_interfaz a_usuario;
  private static String nombre_usuario;

  public static void establecer_conexion(String servidor, String puerto) {
    try {
      a_usuario = (s_rmifs_interfaz)
        Naming.lookup("rmi://"+servidor+":"+puerto+"/s_rmifs_Service");
    }
    catch(Exception e) {
      System.out.println("Excepcion encontrada del tipo: "+e);
    }
  }

	public static validador_usuario obtener_usuario_teclado() {
		Scanner sc;
		String nombre_usuario, clave_usuario;

		sc = new Scanner(System.in);
		System.out.print("Nombre: ");
		nombre_usuario = sc.next();
		System.out.print("Clave: ");
		clave_usuario = sc.next();

		return new validador_usuario(nombre_usuario, clave_usuario);
	}

	public static validador_usuario obtener_usuario_archivo(File archivo) {
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
		    return new validador_usuario(dupla[0],dupla[1]);
		  }
		  sc.close();
		}
		catch(FileNotFoundException e) {
		  System.out.println("El archivo de datos de los usuarios no fue encontrado.");
		}
    return null;
	}

  public static boolean validar(validador_usuario usuario) {
    boolean respuesta = false;

    try {
      respuesta = a_usuario.validar(usuario.getNombre(), usuario.getClave());
    }
    catch(RemoteException e) {
      System.out.println("Error en la validacion del usuario.");
    }
    
    return respuesta;
  }

	public static void interpretar_comandos_archivo(File archivo) {
		Scanner sc;
		String linea;
		String[] dupla;

		try {
		  sc = new Scanner(archivo);
		  while(sc.hasNextLine()) {
		    linea = sc.nextLine();
		    dupla = linea.split("\\s+");
		    ejecutar_comando(dupla);
		  }
		}
		catch(FileNotFoundException e) {
		  System.out.println("El archivo de datos de los comandos no fue encontrado");
		}
	}

	public static void interpretar_comandos_teclado() {
		Scanner sc;
		String comando;
		String [] dupla;

		sc = new Scanner(System.in);
		System.out.println("Introduzca su comando:\n");
		while(sc.hasNextLine()) {
		  comando = sc.nextLine();
		  dupla = comando.split("\\s+");
      System.out.println(dupla.length);
		  ejecutar_comando(dupla);
		}
	}

	public static void info() {
		System.out.println("Comandos disponibles:\n"
		  +"rls : Muestra la lista de archivos disponibles en el servidor.\n"
		  +"lls : Muestra la lista de archivos locales disponibles.\n"
		  +"sub archivo : Sube archivo al servidor remoto.\n"
		  +"baj archivo : Descarga archivo del servidor remoto.\n"
		  +"bor archivo : Borra archivo del servidor remoto.\n"
		  +"info : Muestra en pantalla esta ayuda.\n"
		  +"sal : Termina la ejecucion del programa.\n");
	}

  public static void lls() {
    File directorio_actual;
    File[] archivos;

    directorio_actual = new File(System.getProperty("user.dir"));
    archivos = directorio_actual.listFiles();
    for(int i=0; i<archivos.length; i++) {
      System.out.println(archivos[i].getName());
    } 
  }

  public static void sub(String archivo) {
    File archivo_subida;
    FileInputStream stream;
    ByteArrayOutputStream b_array;
    byte[] buffer;
    byte[] buffer_subida;

    try {
      archivo_subida = new File(archivo);
      stream = new FileInputStream(archivo_subida);
      b_array = new ByteArrayOutputStream();
      buffer = new byte[1024];
      
      for(int lectura; (lectura = stream.read(buffer)) != -1;) {
        b_array.write(buffer, 0, lectura);
      }
      buffer_subida = b_array.toByteArray();
      a_usuario.sub(nombre_usuario, archivo, buffer_subida);

    }
    catch(IOException e) {
      System.out.println("Error en la lectura del archivo a subir.");
    }
  }

  public static void baj(String nombre_archivo) {
    File archivo_guardado;
    FileOutputStream stream;
    ByteArrayOutputStream b_array;
    byte[] buffer_descarga;
    try {
      buffer_descarga = a_usuario.baj(nombre_archivo);
      archivo_guardado = new File(nombre_archivo);
      stream = new FileOutputStream(archivo_guardado);
      stream.write(buffer_descarga);
      stream.flush();
      stream.close();   
    }
    catch(FileNotFoundException e) {
      System.out.println("El archivo a descargar no fue encontrado.");
    }
    catch(IOException e) {
      System.out.println("Error de E/S en el archivo a descargar.");
    }
  }

	public static void ejecutar_comando(String[] instruccion) {
    try {
      if(instruccion[0].equals("rls")) {
        a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
  		  System.out.println(a_usuario.rls());
  		}
  		if(instruccion[0].equals("lls")) {
  		  a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        lls();
  		}
  		if(instruccion[0].equals("sub")) {
  		  a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        sub(instruccion[1]);
  		}
  		if(instruccion[0].equals("baj")) {
  		  a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        baj(instruccion[1]);
  		}
  		if(instruccion[0].equals("bor")) {
        a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
	  	  if(a_usuario.bor(nombre_usuario, instruccion[1])) {
          System.out.println("El archivo "+instruccion[1]+" ha sido borrado.");
        }
        else {
          System.out.println("EL archivo "+instruccion[1]+" no pudo ser borrado.");
        }
  		}
  		if(instruccion[0].equals("info")) {
	  	  a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        info();
	  	}
	  	if(instruccion[0].equals("sal")) {
		    a_usuario.agregar_instruccion(nombre_usuario,instruccion[0]);
        System.exit(0);
		  }
	  }
    catch(RemoteException e) {
      System.out.println("Ha ocurrido un error en la comunicacion con el servidor.");
    }
  }

}
