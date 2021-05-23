package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.TipoMensaje;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendUploaderInfoToDownloader
        extends MensajeDelServidor {

    private static final Logger LOGGER = SocketReadyLogger.create(SendUploaderInfoToDownloader.class.getName());

    private final String ipCliente;
    private final int puertoCliente;
    private final boolean exito;

    public SendUploaderInfoToDownloader(String ipCliente, int puertoCliente, boolean exito, String origin,
                                        String destiny)
    {
        super(TipoMensaje.MANDAR_INFO_CLIENTE_A_CLIENTE, origin, destiny);
        this.ipCliente = ipCliente;
        this.puertoCliente = puertoCliente;
        this.exito = exito;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        if (exito)  // All goes well
        {
            try
            {
                cliente.requestDownload(ipCliente, puertoCliente);
                cliente.mostrarTextoPorConsola("Download has started");
                LOGGER.info("Download has started");
            }
            catch (IOException e)
            {
                cliente.mostrarTextoPorConsola("Error during download");
                LOGGER.log(Level.SEVERE, "Error during download", e);
            }
        }
        else  // Uploader does not exist or sth else
        {
            cliente.mostrarTextoPorConsola("Could not start download: user might not exist");
            LOGGER.info("Could not start download: user might not exist");
        }
    }
}
