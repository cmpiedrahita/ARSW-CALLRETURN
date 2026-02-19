package ejercicio3;

import java.net.*;
import java.io.*;
import java.nio.file.*;

/**
 * Servidor web que soporta múltiples solicitudes seguidas.
 * Retorna archivos HTML e imágenes solicitados.
 */
public class HttpServer {
    private static final int PORT = 35003;
    private static final String WEB_ROOT = "www";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor HTTP iniciado en puerto " + PORT);

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                handleRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(out, true);

        // Leer primera línea del request (GET /archivo.html HTTP/1.1)
        String requestLine = in.readLine();
        if (requestLine == null) return;

        System.out.println("Request: " + requestLine);

        // Leer headers hasta línea vacía
        while (in.ready() && !in.readLine().isEmpty());

        // Parsear el path solicitado
        String[] parts = requestLine.split(" ");
        if (parts.length < 2) return;

        String path = parts[1];
        if (path.equals("/")) path = "/index.html";

        File file = new File(WEB_ROOT + path);

        if (file.exists() && !file.isDirectory()) {
            // Archivo existe
            String contentType = getContentType(path);
            byte[] fileContent = Files.readAllBytes(file.toPath());

            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: " + contentType);
            writer.println("Content-Length: " + fileContent.length);
            writer.println();
            writer.flush();
            out.write(fileContent);
            out.flush();
        } else {
            // Archivo no encontrado
            String response = "<html><body><h1>404 Not Found</h1></body></html>";
            writer.println("HTTP/1.1 404 Not Found");
            writer.println("Content-Type: text/html");
            writer.println("Content-Length: " + response.length());
            writer.println();
            writer.println(response);
        }
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";
        return "text/plain";
    }
}
