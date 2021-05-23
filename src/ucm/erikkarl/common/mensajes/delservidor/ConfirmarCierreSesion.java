package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;

import java.util.logging.Logger;

/**
 * {@link Mensaje} para confirmarle al cliente si se ha cerrado sesion con exito o no.
 */
public final class ConfirmarCierreSesion
        extends MensajeDelServidor
{
    private static final Logger LOGGER = SocketReadyLogger.create(ConfirmarCierreSesion.class.getName());
    private final boolean cierreSesionConExito;

    public ConfirmarCierreSesion(String origin, String destiny, boolean cierreSesionConExito) {
        super(TipoMensaje.CONFIRMAR_CIERRE_SESION, origin, destiny);
        this.cierreSesionConExito = cierreSesionConExito;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        cliente.confirmarCierreSesion(cierreSesionConExito);
        var word = cierreSesionConExito ? "succesful" : "unsuccesful";
        cliente.mostrarTextoPorConsola("Logging out was " + word);

        if (!cierreSesionConExito)
            LOGGER.warning("Logging out was unsuccesful");
    }
}
