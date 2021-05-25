package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.exceptions.UserOfflineOrDoesNotExistException;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.PeticionFicheroACliente;
import ucm.erikkarl.common.mensajes.delservidor.SendUploaderInfoToDownloader;
import ucm.erikkarl.common.server.SesionServidor;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeticionFicheroAServidor
        extends MensajeDelCliente
{
    private static final Logger LOGGER = SocketReadyLogger.create(PeticionFicheroAServidor.class.getName());
    private final String filename;
    private final String uploader;
    private final String downloader;

    public PeticionFicheroAServidor(String origin, String destiny, String filename, String uploader, String downloader)
    {
        super(TipoMensaje.PETICION_FICHERO_A_SERVIDOR, origin, destiny);
        this.filename = filename;
        this.uploader = uploader;
        this.downloader = downloader;
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        try
        {
            mandarPeticionFicheroACliente(sesionServidor);
        }
        catch (UserOfflineOrDoesNotExistException e)
        {
            mandarErrorPorUsuarioInexistente(sesionServidor, e);
        }
        catch (FileNotFoundException e)
        {
            mandarErrorPorFicheroInexistente(sesionServidor, e);
        }
    }

    private void mandarPeticionFicheroACliente(final SesionServidor sesionServidor)
            throws UserOfflineOrDoesNotExistException, FileNotFoundException
    {
        var msg = new PeticionFicheroACliente(origin, destiny, filename, downloader);
        var uploaderUser = sesionServidor.datosUsuarios().get(uploader);

        if (uploaderUser.isEmpty())
            throw new UserOfflineOrDoesNotExistException("Could not find user");

        else if (!uploaderUser.get().ficherosDisponibles().contains(filename))
            throw new FileNotFoundException("User does not have the following file: " + filename);

        sesionServidor.mandarMensajeACliente(msg, uploader);
        LOGGER.fine("Petition sent to client");
    }

    private void mandarErrorPorUsuarioInexistente(final SesionServidor sesionServidor,
                                                  final UserOfflineOrDoesNotExistException e)
    {
        var errorMsg = new SendUploaderInfoToDownloader(null, -1, false, destiny, origin);
        sesionServidor.mandarMensajeACliente(errorMsg);
        LOGGER.log(Level.INFO, "User is offline or does not exist", e);
    }

    private void mandarErrorPorFicheroInexistente(final SesionServidor sesionServidor, final FileNotFoundException e) {
        var errorMsg = new SendUploaderInfoToDownloader(null, -1, false, destiny, origin);
        sesionServidor.mandarMensajeACliente(errorMsg);
        LOGGER.log(Level.INFO, "User does not have this file", e);
    }
}
