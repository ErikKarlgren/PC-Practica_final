package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.exceptions.UserOfflineOrDoesNotExistException;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.SendUploaderInfoToDownloader;
import ucm.erikkarl.common.server.SesionServidor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SendUploaderInfoToServer
        extends MensajeDelCliente
{
    private static final Logger LOGGER = SocketReadyLogger.create(SendUploaderInfoToServer.class.getName());
    private final String ipCliente;
    private final int puertoCliente;
    private final String nombreDescargador;
    private final boolean exito;

    public SendUploaderInfoToServer(String nombreDescargador, int puertoCliente, boolean exito, String origin,
                                    String destiny)
    {
        super(TipoMensaje.MANDAR_INFO_CLIENTE_A_SERVIDOR, origin, destiny);
        this.exito = exito;
        ipCliente = origin;
        this.puertoCliente = puertoCliente;
        this.nombreDescargador = nombreDescargador;
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        if (exito)  // All goes well
            sendInfoToDownloader(sesionServidor, puertoCliente, true);
        else  // There's sth wrong with the uploader
            sendInfoToDownloader(sesionServidor, -1, false);
    }

    private void sendInfoToDownloader(final SesionServidor sesionServidor, final int puertoCliente, final boolean b) {
        try
        {
            var msg = new SendUploaderInfoToDownloader(ipCliente, puertoCliente, b, origin, destiny);
            sesionServidor.mandarMensajeACliente(msg, nombreDescargador);
        }
        catch (UserOfflineOrDoesNotExistException e)
        {
            LOGGER.log(Level.SEVERE, "Downloader should not be offline or should exist", e);
        }
    }
}
