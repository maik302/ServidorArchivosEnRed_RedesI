import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public static validador_usuario obtener_usuario_teclado() {
  Scanner sc;
  String nombre, clave;

  sc = new Scanner(System.in);
  System.out.print("Nombre: ");
  nombre = sc.next();
  System.out.print("Clave: ");
  clave = sc.next();

  return new validador_usuario(nombre,clave);
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
}

public static void interpretar_comandos_archivo(File archivo) {
  Scanner sc;
  String linea;
  String[] dupla;

  try {
    sc = new Scanner(archivo);
    while(sc.hasNextLine()) {
      linea = sc.nextLine();
      dupla = linea.split(" ");
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
  System.out.println("Introduzca su comando:");
  while(sc.hasNext()) {
    comando = sc.next();
    dupla = comando.split(" ");
    ejecutar_comando(dupla);
  }
}

public static void ejecutar_comando(String[] instruccion) {
  if(instruccion[0].equals("rls")) {
    //Funcion rls
  }
  if(instruccion[0].equals("lls")) {
    //Funcion lls
  }
  if(instruccion[0].equals("sub")) {
    //Funcion sub
  }
  if(instruccion[0].equals("baj")) {
    //Funcion baj
  }
  if(intruccion[0].equals("bor")) {
    //Funcion bor
  }
  if(instruccion[0].equeals("info")) {
    //Funcion info
  }
  if(instruccion[0].equals("sal")) {
    System.exit(0);
  }
}
