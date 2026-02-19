package ejercicio4;

import java.io.IOException;
import java.net.*;
import java.util.Date;

/**
 * Servidor de tiempo usando datagramas UDP.
 * Responde con la hora actual cuando recibe una solicitud.
 */
public class DatagramTimeServer {
    private DatagramSocket socket;
    private static final int PORT = 4445;

    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("Servidor de tiempo iniciado en puerto " + PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        byte[] buf = new byte[256];

        while (true) {
            try {
                // Recibir solicitud
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // Preparar respuesta con la hora actual
                String timeString = new Date().toString();
                buf = timeString.getBytes();

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                // Enviar respuesta
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);

                System.out.println("Hora enviada a " + address + ":" + port);
                buf = new byte[256];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DatagramTimeServer server = new DatagramTimeServer();
        server.startServer();
    }
}
