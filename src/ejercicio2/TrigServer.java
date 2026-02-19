package ejercicio2;

import java.net.*;
import java.io.*;

/**
 * Servidor que calcula funciones trigonométricas.
 * Por defecto calcula coseno, pero puede cambiar a seno o tangente.
 * Escucha en el puerto 35002.
 */
public class TrigServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        
        // Crear el socket del servidor en el puerto 35002
        try {
            serverSocket = new ServerSocket(35002);
            System.out.println("Servidor iniciado en puerto 35002");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35002.");
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

        // Función actual (por defecto coseno)
        String currentFunction = "cos";
        String inputLine;

        // Procesar mensajes del cliente
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);

            // Comando para salir
            if (inputLine.equalsIgnoreCase("exit")) {
                break;
            } 
            // Comando para cambiar función (fun:sin, fun:cos, fun:tan)
            else if (inputLine.startsWith("fun:")) {
                currentFunction = inputLine.substring(4);
                out.println("Función cambiada a: " + currentFunction);
            } 
            // Calcular función trigonométrica
            else {
                try {
                    double number = Double.parseDouble(inputLine);
                    double result = 0;

                    // Aplicar la función actual
                    switch (currentFunction) {
                        case "sin":
                            result = Math.sin(number);
                            break;
                        case "cos":
                            result = Math.cos(number);
                            break;
                        case "tan":
                            result = Math.tan(number);
                            break;
                        default:
                            out.println("Función no válida");
                            continue;
                    }
                    
                    // Corregir errores de precisión de punto flotante
                    if (Math.abs(result) < 1e-10) {
                        result = 0.0;
                    }
                    
                    out.println(result);
                } catch (NumberFormatException e) {
                    out.println("Error: Envíe un número válido o comando fun:");
                }
            }
        }

        // Cerrar recursos
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
