import java.util.ArrayList;

public class HistorialUsuarios {

  private ArrayList<Log> historial;

  private static class Log {
    String usuario;
    String instruccion;
  }

  public HistorialUsuarios() {
    historial = new ArrayList<Log>();
  }

  public void agregar_instruccion(String usuario, String instruccion) {
    Log caja = new Log();

    caja.usuario = usuario;
    caja.instruccion = instruccion;
    historial.add(caja);
  }

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
