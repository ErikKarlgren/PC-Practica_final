package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.ConfirmarCierreSesion;
import ucm.erikkarl.common.server.SesionServidor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Mensaje} para pedirle al servidor que cierre la sesion del usuario actual.
 */
public final class PeticionCierreSesion
        extends MensajeDelCliente
{
    private static final Logger LOGGER = SocketReadyLogger.create(PeticionCierreSesion.class.getName());

    public PeticionCierreSesion(String origin, String destiny) {
        super(TipoMensaje.PETICION_CIERRE_SESION, origin, destiny);
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        var cierreSesionConExito = sesionServidor.cerrarSesion();
        var msg = new ConfirmarCierreSesion(destiny, origin, cierreSesionConExito);
        var texto = "Cierre de sesion " + (cierreSesionConExito ? "con" : "sin") + " exito";

        LOGGER.log(cierreSesionConExito ? Level.INFO : Level.WARNING, texto);
        sesionServidor.mandarMensajeACliente(msg);
        sesionServidor.cerrarConexion();
    }
}
