package ucm.erikkarl.cliente.oyenteaservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.concurrency.MessagesQueue;
import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Manda mensajes al servidor guardados en la cola de mensajes pendientes que tiene
 * el cliente.
 */
public class Emisor
        implements Runnable {

    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(Emisor.class.getName());

    private final Cliente cliente;
    private final MessagesQueue queue;
    private final ObjectOutputStream out;

    public Emisor(Cliente cliente, MessagesQueue queue, ObjectOutputStream out, Socket socket) {
        this.cliente = cliente;
        this.queue = queue;
        this.out = out;
        LOGGER.setSocket(socket);
    }

    @Override
    public void run() {
        try
        {
            while (cliente.isConnectedToServer())
            {
                var msg = queue.poll();
                out.writeObject(msg);
                LOGGER.info("Sent the following message to server: " + msg);
            }
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during connection to server", e);
        }
        catch (InterruptedException e)
        {
            LOGGER.log(Level.WARNING, "Thread was interrupted", e);
            Thread.currentThread().interrupt();
        }
        finally
        {
            LOGGER.info("Emitter thread has finished");
        }
    }
}
