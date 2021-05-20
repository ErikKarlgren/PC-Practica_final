package ucm.erikkarl.server.oyenteacliente;

import ucm.erikkarl.common.Oyente;
import ucm.erikkarl.common.concurrency.MessagesQueue;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.server.SesionServidor;
import ucm.erikkarl.server.Servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Recibe mensajes del cliente, le pide al {@link Servidor} procesarlos, y manda mensajes de confirmacion
 * de vuelta al cliente.
 */
public class OyenteACliente
        extends Oyente {

    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(OyenteACliente.class.getName());

    private final SesionServidor sesionServidor;
    private final MessagesQueue queue;

    public OyenteACliente(SesionServidor sesionServidor, Socket socket, MessagesQueue messagesQueue) {
        super(socket);
        this.sesionServidor = sesionServidor;
        this.queue = messagesQueue;
        LOGGER.setSocket(socket);
    }

    @Override
    protected void runUsingSocket(ObjectInputStream in, ObjectOutputStream out) throws InterruptedException {
        var emisorThread = new Thread(new Emisor(sesionServidor, queue, out, socket));
        var receptorThread = new Thread(new Receptor(sesionServidor, in, socket));

        LOGGER.info("Starting emitter thread");
        emisorThread.start();

        LOGGER.info("Starting receiver thread");
        receptorThread.start();

        receptorThread.join();
        // Interrumpimos el hilo porque si no se queda bloqueado leyendo de la cola de mensajes
        emisorThread.interrupt();
        emisorThread.join();
        LOGGER.info("Both the emitter and the receiver threads have finished");
    }
}
