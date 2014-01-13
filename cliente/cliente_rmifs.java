import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class cliente_rmifs {

  public static void main(String[] args) {
    try {
      a_rmifs_interfaz a_usuario = (a_rmifs_interfaz)
        //Esta opcion comentada debe ser probada en la Universidad, 
        //por la lejania del servidor tarda en responder.
        Naming.lookup("rmi://localhost:21000/a_rmifs_interfazService");
      a_usuario.obtener_usuario_teclado();
      //System.out.println(a_usuario.usuario.toString());
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
