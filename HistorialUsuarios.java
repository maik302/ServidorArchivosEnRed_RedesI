/*
 * HistorialUsuarios
 *
 * @ Grupo 50
 * @ Autores:
 * Michael Woo 09-10912
 * Luis Esparragoza 08-10337
 *
 */

import java.util.ArrayList;

/*
 * Clase que registra el historial de comandos de los usuarios que interactuan
 * con el servidor de archivos.
 */
public class HistorialUsuarios {

  private ArrayList<Log> historial;

 /* 
  * Definimos la clase privada log con los atributos atributos 
  * usuario e instruccion para agregarlos al historial
  */
  private static class Log {
    String usuario;
    String instruccion;
  }

 /* 
  * Definimos la clase HistorialUsuarios de tipo lista de log
  * que sera el historial de comandos con sus usuarios
  */
  public HistorialUsuarios() {
    historial = new ArrayList<Log>();
  }

 /* 
  * Agrega una instruccion nueva al historial de comandos
  *
  * @param usuario: el usuario que escribe el comando
  * @param instruccion: la instruccion suministrada por el usuario
  */
  public void agregar_instruccion(String usuario, String instruccion) {
    Log caja = new Log();

    caja.usuario = usuario;
    caja.instruccion = instruccion;
    historial.add(caja);
  }

 /* 
  * Imprime el historial de comandos hasta el n-comando, 
  * que hayan sido enviados al servidor
  *
  * @param num: entero que delimita cuantos comandos imprimir
  */
  public void imprimir_historial(int num) {
    int index;

    index = historial.size()-1;
    if(historial.size() < num) {
      for(int i=index; i>0; i--) {
        System.out.println(historial.get(i).usuario+" : "+historial.get(i).instruccion);
      }
    }
    else {
      for(int i=index; i>historial.size()-num; i--) {
        System.out.println(historial.get(i).usuario+" : "+historial.get(i).instruccion);
      }
    }
  }

}
