package ucm.erikkarl.cliente.downloading;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Clase encargada de crear tareas de descarga concurrentes.
 */
public class DownloadManager {
    private static final Logger LOGGER = SocketReadyLogger.create(DownloadManager.class.getName());

    private final Cliente cliente;

    public DownloadManager(Cliente cliente) {this.cliente = cliente;}

    /**
     * This operation blocks until a conection is made with a {@link java.net.ServerSocket}.
     * After it, the new download runs in a different thread.
     *
     * @param remoteIP   client's ip
     * @param remotePort client's port
     */
    public void requestDownload(String remoteIP, int remotePort) throws IOException {
        var socket = new Socket(remoteIP, remotePort);
        var downloadThread = new Thread(new Downloader(cliente, socket));
        downloadThread.start();
        LOGGER.fine("New download task has started");
    }
}
