package ejercicio5;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface remota para el servicio de chat.
 */
public interface ChatService extends Remote {
    void receiveMessage(String from, String message) throws RemoteException;
}
