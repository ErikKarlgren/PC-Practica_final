package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;

/**
 * {@link Mensaje} para confirmarle al cliente si se ha iniciado sesion con exito o no.
 */
public final class ConfirmarInicioSesion
        extends MensajeDelServidor {

    private final boolean inicioSesionConExito;

    public ConfirmarInicioSesion(String origin, String destiny, boolean inicioSesionConExito) {
        super(TipoMensaje.CONFIRMAR_INICIO_SESION, origin, destiny);
        this.inicioSesionConExito = inicioSesionConExito;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        cliente.confirmarInicioSesion(inicioSesionConExito);
        cliente.mostrarTextoPorConsola("User was connected to server");
    }
}
