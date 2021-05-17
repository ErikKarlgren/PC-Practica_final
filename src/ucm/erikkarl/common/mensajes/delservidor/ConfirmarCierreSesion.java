package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;

/**
 * {@link Mensaje} para confirmarle al cliente si se ha cerrado sesion con exito o no.
 */
public final class ConfirmarCierreSesion
        extends MensajeDelServidor {

    private final boolean cierreSesionConExito;

    public ConfirmarCierreSesion(String origin, String destiny, boolean cierreSesionConExito) {
        super(TipoMensaje.CONFIRMAR_CIERRE_SESION, origin, destiny);
        this.cierreSesionConExito = cierreSesionConExito;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        cliente.confirmarCierreSesion(cierreSesionConExito);
    }
}
