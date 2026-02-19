package ejercicio1;

import java.net.*;
import java.io.*;

/**
 * Servidor que calcula el cuadrado de números recibidos.
 * Escucha en el puerto 35001.
 */
public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        
        // Crear el socket del servidor en el puerto 35001
        try {
            serverSocket = new ServerSocket(35001);
            System.out.println("Servidor iniciado en puerto 35001");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35001.");
            System.exit(1);
        }

        // Esperar conexión del cliente
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        // Crear flujos de entrada y salida
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        // Procesar mensajes del cliente
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);
            
            // Comando para salir
            if (inputLine.equalsIgnoreCase("exit")) {
                break;
            }
            
            // Calcular el cuadrado del número
            try {
                double number = Double.parseDouble(inputLine);
                double result = number * number;
                out.println(result);
            } catch (NumberFormatException e) {
                out.println("Error: Envíe un número válido");
            }
        }

        // Cerrar recursos
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
