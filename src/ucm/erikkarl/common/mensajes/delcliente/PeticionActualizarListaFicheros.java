package ucm.erikkarl.common.mensajes.delcliente;

import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.mensajes.delservidor.ConfirmarActualizarListaFicheros;
import ucm.erikkarl.common.server.SesionServidor;
import ucm.erikkarl.common.server.Usuario;

import java.util.LinkedList;
import java.util.logging.Logger;

public class PeticionActualizarListaFicheros
        extends MensajeDelCliente
{
    private static final Logger LOGGER = SocketReadyLogger.create(PeticionActualizarListaFicheros.class.getName());
    private final String nuevoFichero;
    private final String nombreUsuario;

    public PeticionActualizarListaFicheros(String nombreUsuario, String nuevoFichero, String origin, String destiny)
    {
        super(TipoMensaje.PETICION_ACTUALIZAR_LISTA_FICHEROS, origin, destiny);
        this.nuevoFichero = nuevoFichero;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void getProcessedBy(SesionServidor sesionServidor) {
        var usu = sesionServidor.datosUsuarios().get(nombreUsuario);
        if (usu.isEmpty())
            mandarMensajeDeErrorPorUsuarioNoEcontrado(sesionServidor);
        else
            actualizarFicherosDelUsuario(sesionServidor, usu.get());
    }

    private void mandarMensajeDeErrorPorUsuarioNoEcontrado(final SesionServidor sesionServidor) {
        LOGGER.warning("User was not found");
        var msg = new ConfirmarActualizarListaFicheros(nuevoFichero, false, destiny, origin);
        sesionServidor.mandarMensajeACliente(msg);
    }

    private void actualizarFicherosDelUsuario(final SesionServidor sesionServidor, final Usuario usuario)
    {
        var ficheros = new LinkedList<>(usuario.ficherosDisponibles());
        ficheros.add(nuevoFichero);
        usuario.setFicherosDisponibles(ficheros);
        sesionServidor.datosUsuarios().put(usuario);
        var msg = new ConfirmarActualizarListaFicheros(nuevoFichero, true, destiny, origin);
        LOGGER.fine(() -> "Ficheros del usuario " + usuario.uid() + " actualizados correctamente");
        sesionServidor.mandarMensajeACliente(msg);
    }
}
