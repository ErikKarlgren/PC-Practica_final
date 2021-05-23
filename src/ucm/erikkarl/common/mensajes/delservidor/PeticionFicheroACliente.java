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

    public PeticionFicheroACliente(String origin, String destiny, String nombreFichero) {
        super(TipoMensaje.PETICION_FICHERO_A_CLIENTE, origin, destiny);
        this.nombreFichero = nombreFichero;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        try
        {
            cliente.requestUpload(nombreFichero);
            int port = cliente.getUploadServerPort();
            var msg = new SendUploaderInfoToServer(cliente.uid(), port, true, origin, destiny);
            cliente.mandarMensajeAServidor(msg);
            LOGGER.fine("Sent petition to uploader");
        }
        catch (IOException e)
        {
            var msg = new SendUploaderInfoToServer(cliente.uid(), -1, false, origin, destiny);
            cliente.mandarMensajeAServidor(msg);
            LOGGER.log(Level.SEVERE, "Error during upload request", e);
        }
    }
}
