package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.MandarDatosUsuarios;
import ucm.erikkarl.common.server.SesionServidor;

/**
 * {@link Mensaje} para pedirle al servidor datos sobre los usuarios registrados en el mismo.
 */
public final class PeticionDatosUsuarios
        extends MensajeDelCliente {

    protected PeticionDatosUsuarios(String origin, String destiny) {
        super(TipoMensaje.PETICION_LISTA_USUARIOS, origin, destiny);
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        var datosUsuarios = sesionServidor.datosUsuarios().mapaUsuarioAEstadoConexion();
        var msg = new MandarDatosUsuarios(datosUsuarios, destiny, origin);
        sesionServidor.mandarMensajeACliente(msg);
    }
}
