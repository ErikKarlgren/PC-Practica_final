package ucm.erikkarl.cliente.oyenteaservidor;

import ucm.erikkarl.cliente.CodigoCliente;
import ucm.erikkarl.common.Oyente;
import ucm.erikkarl.common.concurrency.MessagesQueue;
import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Clase para que el cliente interactue con el servidor. Usa un {@link Socket}
 * que se cierra automaticamente al finalizar la ejecucion.
 */
public class OyenteAServidor
        extends Oyente {

    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(OyenteAServidor.class.getName());

    private final CodigoCliente cliente;
    private final MessagesQueue queue;

    /**
     * Crea un {@link OyenteAServidor} a partir de un {@link Socket}.
     *
     * @param socket  {@link Socket} para oir al servidor.
     * @param queue   cola de tareas pendientes para mandar al servidor.
     * @param cliente {@link ucm.erikkarl.common.cliente.Cliente} dueño del socket.
     */
    public OyenteAServidor(CodigoCliente cliente, Socket socket, MessagesQueue queue) {
        super(socket);
        this.cliente = cliente;
        this.queue = queue;
        LOGGER.setSocket(socket);
    }

    @Override
    protected void runUsingSocket(ObjectInputStream in, ObjectOutputStream out) throws InterruptedException {
        var emisorThread = new Thread(new Emisor(cliente, queue, out, socket), "emisor");
        var receptorThread = new Thread(new Receptor(cliente, in, socket), "receptor");

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
