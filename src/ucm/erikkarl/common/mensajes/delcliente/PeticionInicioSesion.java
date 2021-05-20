package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.ConfirmarInicioSesion;
import ucm.erikkarl.common.server.SesionServidor;
import ucm.erikkarl.common.server.Usuario;

/**
 * {@link Mensaje} para pedirle al servidor que inicie la sesion de un usuario.
 */
public final class PeticionInicioSesion
        extends MensajeDelCliente {

    private final Usuario usuario;

    public PeticionInicioSesion(Usuario usuario, String origin, String destiny) {
        super(TipoMensaje.PETICION_INICIO_SESION, origin, destiny);
        this.usuario = usuario;
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        var exito = sesionServidor.iniciarSesion(usuario);
        var msg = new ConfirmarInicioSesion(destiny, origin, exito);
        sesionServidor.mandarMensajeACliente(msg);
    }
}