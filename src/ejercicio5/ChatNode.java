package ejercicio5;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;

/**
 * Nodo de chat que puede enviar y recibir mensajes usando RMI.
 */
public class ChatNode implements ChatService {
    private String myName;

    public ChatNode(String name, int port) {
        this.myName = name;
        try {
            // Exportar este objeto como servicio remoto
            ChatService stub = (ChatService) UnicastRemoteObject.exportObject(this, 0);

            // Crear o obtener el registry en el puerto especificado
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(port);
                System.out.println("Registry creado en puerto " + port);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(port);
                System.out.println("Conectado a registry en puerto " + port);
            }

            // Publicar el servicio
            registry.rebind(name, stub);
            System.out.println("Chat '" + name + "' listo en puerto " + port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(String from, String message) throws RemoteException {
        System.out.println("\n[" + from + "]: " + message);
        System.out.print("> ");
    }

    public void startChat() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nComandos:");
        System.out.println("  connect <ip> <puerto> <nombre>");
        System.out.println("  send <mensaje>");
        System.out.println("  exit");

        String remoteIp = null;
        int remotePort = 0;
        String remoteName = null;

        while (true) {
            System.out.print("> ");
            String input = console.readLine();
            if (input == null) break;

            String[] parts = input.split(" ", 2);
            String command = parts[0];

            if (command.equalsIgnoreCase("exit")) {
                break;
            } else if (command.equalsIgnoreCase("connect")) {
                String[] params = parts[1].split(" ");
                if (params.length < 3) {
                    System.out.println("Uso: connect <ip> <puerto> <nombre>");
                    continue;
                }
                remoteIp = params[0];
                remotePort = Integer.parseInt(params[1]);
                remoteName = params[2];
                System.out.println("Conectado a " + remoteName + " en " + remoteIp + ":" + remotePort);
            } else if (command.equalsIgnoreCase("send")) {
                if (remoteIp == null) {
                    System.out.println("Primero conecta con: connect <ip> <puerto> <nombre>");
                    continue;
                }
                String message = parts.length > 1 ? parts[1] : "";
                sendMessage(remoteIp, remotePort, remoteName, message);
            } else {
                System.out.println("Comando desconocido");
            }
        }
    }

    private void sendMessage(String ip, int port, String name, String message) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            ChatService remote = (ChatService) registry.lookup(name);
            remote.receiveMessage(myName, message);
            System.out.println("Mensaje enviado");
        } catch (Exception e) {
            System.out.println("Error al enviar: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: ChatNode <nombre> <puerto>");
            return;
        }

        String name = args[0];
        int port = Integer.parseInt(args[1]);

        ChatNode node = new ChatNode(name, port);
        node.startChat();
    }
}
