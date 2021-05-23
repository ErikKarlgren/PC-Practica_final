package ucm.erikkarl.cliente.uploading;

import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class UploadManager {
    private static final Logger LOGGER = SocketReadyLogger.create(UploadManager.class.getName());

    private final ServerSocket serverSocket;

    public UploadManager() throws IOException {
        try
        {
            serverSocket = new ServerSocket(0);
            LOGGER.fine("Server socket was created");
        }
        catch (IOException e)
        {
            throw new IOException("Error while creating the upload manager");
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    /**
     * Blocks until a connection is made with another {@link java.net.Socket}. After that
     * the upload keeps going in a different thread.
     *
     * @param fichero file to be uploaded
     * @throws IOException if an I/O error occcurs when waiting for a connection
     */
    public void requestUpload(String fichero) throws IOException {
        var socket = serverSocket.accept();
        var uploaderThread = new Thread(new Uploader(socket, fichero));
        uploaderThread.start();
        LOGGER.info("Upload has started");
    }
}
