package ucm.erikkarl.cliente.oyenteaservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delservidor.MensajeDelServidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Recibe mensajes del servidor y los manda procesar.
 */
public class Receptor
        implements Runnable {

    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(Receptor.class.getName());

    private final Cliente cliente;
    private final ObjectInputStream in;

    public Receptor(Cliente cliente, ObjectInputStream in, Socket socket) {
        this.cliente = cliente;
        this.in = in;
        LOGGER.setSocket(socket);
    }

    @Override
    public void run() {
        try
        {
            while (cliente.isConnectedToServer())
            {
                var msg = (MensajeDelServidor) in.readObject();
                LOGGER.info("Processing following message: " + msg);
                msg.getProcessedBy(cliente);
            }
        }
        catch (ClassNotFoundException | IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during connection to server", e);
        }

        finally
        {
            LOGGER.info("Receiver thread has finished");
        }
    }
}
