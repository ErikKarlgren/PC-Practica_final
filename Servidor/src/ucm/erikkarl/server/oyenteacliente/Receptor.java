package ucm.erikkarl.server.oyenteacliente;

import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delcliente.MensajeDelCliente;
import ucm.erikkarl.common.server.SesionServidor;

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

    private final SesionServidor sesionServidor;
    private final ObjectInputStream in;

    public Receptor(SesionServidor sesionServidor, ObjectInputStream in, Socket socket) {
        this.sesionServidor = sesionServidor;
        this.in = in;
        LOGGER.setSocket(socket);
    }

    @Override
    public void run() {
        try
        {
            while (sesionServidor.conexionAbierta())
            {
                var msg = (MensajeDelCliente) in.readObject();
                msg.getProcessedBy(sesionServidor);
            }
        }
        catch (ClassNotFoundException | IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during connection to client", e);
        }
    }
}
