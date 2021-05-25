package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delcliente.SendUploaderInfoToServer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeticionFicheroACliente
        extends MensajeDelServidor {
    private final String nombreFichero;

    private static final Logger LOGGER = SocketReadyLogger.create(PeticionFicheroACliente.class.getName());
private final String downloader;

    public PeticionFicheroACliente(String origin, String destiny, String nombreFichero, final String downloader) {
        super(TipoMensaje.PETICION_FICHERO_A_CLIENTE, origin, destiny);
        this.nombreFichero = nombreFichero;
        this.downloader = downloader;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        try
        {
            int port = cliente.getUploadServerPort();
            var msg = new SendUploaderInfoToServer(downloader, port, true, origin, destiny);
            cliente.mandarMensajeAServidor(msg);
            LOGGER.fine("Sending petition to uploader");
            cliente.requestUpload(nombreFichero);
        }
        catch (IOException e)
        {
            var msg = new SendUploaderInfoToServer(downloader, -1, false, origin, destiny);
            cliente.mandarMensajeAServidor(msg);
            LOGGER.log(Level.SEVERE, "Error during upload request", e);
        }
    }
}
