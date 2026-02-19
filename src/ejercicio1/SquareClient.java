package ejercicio1;

import java.io.*;
import java.net.*;

/**
 * Cliente que se conecta al servidor de cuadrados.
 * Envía números y recibe sus cuadrados.
 */
public class SquareClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        // Conectar al servidor en localhost:35001
        try {
            socket = new Socket("127.0.0.1", 35001);
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

        System.out.println("Ingrese números para calcular su cuadrado ('exit' para salir):");
        while ((userInput = stdIn.readLine()) != null) {
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            // Enviar número al servidor y mostrar respuesta
            out.println(userInput);
            System.out.println("Cuadrado: " + in.readLine());
        }

        // Cerrar recursos
        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
