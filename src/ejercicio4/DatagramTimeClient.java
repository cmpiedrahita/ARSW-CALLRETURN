package ejercicio4;

import java.io.IOException;
import java.net.*;

/**
 * Cliente que solicita la hora al servidor cada 5 segundos.
 * Si no recibe respuesta, mantiene la última hora conocida.
 */
public class DatagramTimeClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 4445;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        String currentTime = "Esperando primera actualización...";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);
            InetAddress address = InetAddress.getByName(SERVER_HOST);

            System.out.println("Cliente iniciado. Actualizando cada 5 segundos...");

            while (true) {
                try {
                    // Enviar solicitud
                    byte[] sendBuf = new byte[256];
                    DatagramPacket sendPacket = new DatagramPacket(
                            sendBuf, sendBuf.length, address, SERVER_PORT);
                    socket.send(sendPacket);

                    // Recibir respuesta
                    byte[] receiveBuf = new byte[256];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                    socket.receive(receivePacket);

                    currentTime = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Hora actualizada: " + currentTime);

                } catch (SocketTimeoutException e) {
                    System.out.println("Servidor no responde. Hora actual: " + currentTime);
                }

                // Esperar 5 segundos
                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
