package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.MandarDatosUsuarios;
import ucm.erikkarl.common.server.SesionServidor;

import java.util.logging.Logger;

/**
 * {@link Mensaje} para pedirle al servidor datos sobre los usuarios registrados en el mismo.
 */
public final class PeticionDatosUsuarios
        extends MensajeDelCliente
{
    private static final Logger LOGGER = SocketReadyLogger.create(PeticionDatosUsuarios.class.getName());

    public PeticionDatosUsuarios(String origin, String destiny) {
        super(TipoMensaje.PETICION_LISTA_USUARIOS, origin, destiny);
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        var datosUsuarios = sesionServidor.datosUsuarios().usuarios();
        var exito = (datosUsuarios == null);
        var msg = new MandarDatosUsuarios(datosUsuarios, exito, destiny, origin);

        if (exito)
            LOGGER.fine("Sending users data to client");
        else
            LOGGER.warning("Users data is corrupted (returned null)");

        sesionServidor.mandarMensajeACliente(msg);
    }
}
