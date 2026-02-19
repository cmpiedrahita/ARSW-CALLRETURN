package ejercicio2;

import java.io.*;
import java.net.*;

/**
 * Cliente que se conecta al servidor trigonométrico.
 * Puede enviar números para calcular funciones o comandos para cambiar la función.
 */
public class TrigClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        // Conectar al servidor en localhost:35002
        try {
            socket = new Socket("127.0.0.1", 35002);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host!.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }

        // Leer entrada del usuario
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Ingrese números o comandos fun:sin, fun:cos, fun:tan (escriba 'exit' para salir):");
        while ((userInput = stdIn.readLine()) != null) {
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            // Enviar comando o número al servidor y mostrar respuesta
            out.println(userInput);
            System.out.println("Respuesta: " + in.readLine());
        }

        // Cerrar recursos
        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
