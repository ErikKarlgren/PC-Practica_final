package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Mensaje} para confirmarle al cliente si se ha iniciado sesion con exito o no.
 */
public final class ConfirmarInicioSesion
        extends MensajeDelServidor
{
    private static final Logger LOGGER = SocketReadyLogger.create(ConfirmarInicioSesion.class.getName());
    private final boolean inicioSesionConExito;

    public ConfirmarInicioSesion(String origin, String destiny, boolean inicioSesionConExito) {
        super(TipoMensaje.CONFIRMAR_INICIO_SESION, origin, destiny);
        this.inicioSesionConExito = inicioSesionConExito;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        cliente.confirmarInicioSesion(inicioSesionConExito);
        var text = inicioSesionConExito ? "User was connected to server" : "User could not connect to the server";
        cliente.mostrarTextoPorConsola(text);
        LOGGER.log(inicioSesionConExito ? Level.INFO : Level.SEVERE, text);
    }
}
