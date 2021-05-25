package ucm.erikkarl.server.oyenteacliente;

import ucm.erikkarl.common.concurrency.MessagesQueue;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.server.SesionServidor;

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

    private final SesionServidor sesionServidor;
    private final MessagesQueue queue;
    private final ObjectOutputStream out;

    public Emisor(SesionServidor sesionServidor, MessagesQueue queue, ObjectOutputStream out, Socket socket) {
        this.sesionServidor = sesionServidor;
        this.queue = queue;
        this.out = out;
        LOGGER.setSocket(socket);
    }

    @Override
    public void run() {
        try
        {
            while (sesionServidor.conexionAbierta())
            {
                var msg = queue.poll();
                out.writeObject(msg);
                out.reset();
                LOGGER.info("Sent the following message to current user: " + msg);
            }
        }
        catch (InterruptedException e)
        {
            LOGGER.log(Level.WARNING, "Thread was interrupted", e);
            Thread.currentThread().interrupt();
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during connection to client", e);
        }
    }
}
