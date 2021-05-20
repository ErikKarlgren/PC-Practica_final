package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.ConfirmarCierreSesion;
import ucm.erikkarl.common.server.SesionServidor;

/**
 * {@link Mensaje} para pedirle al servidor que cierre la sesion del usuario actual.
 */
public final class PeticionCierreSesion
        extends MensajeDelCliente {

    public PeticionCierreSesion(String origin, String destiny) {
        super(TipoMensaje.PETICION_CIERRE_SESION, origin, destiny);
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        var cierreSesionConExito = sesionServidor.cerrarSesion();
        var msg = new ConfirmarCierreSesion(destiny, origin, cierreSesionConExito);
        sesionServidor.mandarMensajeACliente(msg);
        sesionServidor.cerrarConexion();
    }
}
